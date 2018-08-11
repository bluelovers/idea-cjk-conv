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
import sc.plugin.cjkconv.replaceSelectionText
import sc.sdk.ApiLog
import javax.swing.Icon

abstract class AbstractAction(icon: Icon? = null) : AnAction(icon), DumbAware
{
	val LOG = ApiLog(javaClass)

	abstract fun _translate(selectedText: String): String

	/**
	 * 执行操作
	 *
	 * @param e              事件
	 * @param editor         编辑器
	 * @param selectionModel
	 */
	protected open fun onActionPerformed(e: AnActionEvent, editor: Editor, selectionModel: SelectionModel)
	{
		LOG.debug("""[onActionPerformed] ${e} ${editor} ${selectionModel}""")

		if (isNotWritable(e, editor, selectionModel))
		{
			return
		}

		runReplaceSelectedAction(e.project!!, editor, selectionModel)
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
					val r = _translate(selectedText)

					LOG.debug("[runReplaceSelectedAction] { ${selectedText} => ${r} }")

					if (!selectedText.equals(r))
					{
						replaceSelectionText(editor, selectionModel, r)
					}
				}
			}
		}
	}

	fun isNotWritable(e: AnActionEvent, editor: Editor, selectionModel: SelectionModel): Boolean
	{
		val project = e.project ?: return true

		e.getData(PlatformDataKeys.VIRTUAL_FILE)?.let { it ->
			if (!it.isWritable())
			{
				return true
			}
		}

		return false
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

	//protected open val AnActionEvent.editor: Editor? get() = CommonDataKeys.EDITOR.getData(dataContext)

	open fun getEditor(e: AnActionEvent): Editor?
	{
		return CommonDataKeys.EDITOR.getData(e.dataContext)
	}

	override fun update(e: AnActionEvent)
	{
		val active = getEditor(e)?.let { editor ->
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

		val editor = getEditor(e) ?: return
		onActionPerformed(e, editor, editor.selectionModel)
	}
}
