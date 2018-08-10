package sc.plugin.cjkconv.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import sc.plugin.cjkconv.MyStringTable
import sc.plugin.cjkconv.TableLoader
import sc.plugin.cjkconv.replaceSelectionText
import sc.plugin.cjkconv.translate
import sc.sdk.ApiLog
import javax.swing.Icon

abstract class AbstractConvAction(val idkey: String, val pid: String, icon: Icon? = null) : AnAction(icon), DumbAware
{
	val LOG = ApiLog(javaClass)
	var _inited = false
	val _hashmap = HashMap<Int, Int>()

	/**
	 * lazy load
	 * 想說等到實際第一次使用才來載入轉換表
	 * 實際上對於記憶體有沒有幫助不曉得
	 */
	fun _load()
	{
		if (!_inited)
		{
			val table = TableLoader(idkey, pid).load()

			MyStringTable(
				string_from = String(table.from as ByteArray),
				string_to = String(table.to as ByteArray)
			).copyTo(_hashmap)

			_inited = true
		}
	}

	/**
	 * 执行操作
	 *
	 * @param e              事件
	 * @param editor         编辑器
	 * @param selectionModel
	 */
	protected open fun onActionPerformed(e: AnActionEvent, editor: Editor, selectionModel: SelectionModel)
	{
		LOG.info("""[onActionPerformed] ${e} ${editor} ${selectionModel}""")

		val project = e.project ?: return
		e.getData(PlatformDataKeys.VIRTUAL_FILE)?.let { it ->
			if (!it.isWritable())
			{
				return
			}
		}

		this._load()

		runReplaceSelectedAction(project, editor, selectionModel)
	}

	/**
	 * 必須要這樣做才能具有寫入權限
	 */
	fun runReplaceSelectedAction(project: Project, editor: Editor, selectionModel: SelectionModel)
	{
		WriteCommandAction.runWriteCommandAction(project) {
			run()
			{
				val selectedText: String? = selectionModel.getSelectedText();
				if (selectedText!!.isNotEmpty())
				{
					val r = translate(selectedText, _hashmap)

					LOG.info("[runReplaceSelectedAction] { ${selectedText} => ${r} }")

					if (!selectedText.equals(r))
					{
						replaceSelectionText(editor, selectionModel, r)
					}
				}
			}
		}
	}

	/**
	 * 更新Action
	 *
	 * @param e      事件
	 * @param active 是否活动的，表示是否可以取到词
	 */
	protected open fun onUpdate(e: AnActionEvent, active: Boolean = true)
	{
		//LOG.info("""[onUpdate] ${e} ${active}""")

		e.presentation.isEnabledAndVisible = active
	}

	protected open val AnActionEvent.editor: Editor? get() = CommonDataKeys.EDITOR.getData(dataContext)

	override fun update(e: AnActionEvent)
	{
		val active = e.editor?.let { editor ->
			editor.selectionModel.hasSelection()
		} ?: false

		//val active = e.editor?.selectionModel?.hasSelection()

		onUpdate(e, active)
	}

	override fun actionPerformed(e: AnActionEvent)
	{
		if (ApplicationManager.getApplication().isHeadlessEnvironment)
		{
			return
		}

		val editor = e.editor ?: return
		onActionPerformed(e, editor, editor.selectionModel)
	}

}
