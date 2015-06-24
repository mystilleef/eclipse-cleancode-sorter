package com.laboki.eclipse.plugin.cleancodesorter.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorPart;

import com.google.common.base.Optional;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.AstContext;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.CompilationUnitContext;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.EditorContext;
import com.laboki.eclipse.plugin.cleancodesorter.events.SortEvent;
import com.laboki.eclipse.plugin.cleancodesorter.instance.EventBusInstance;
import com.laboki.eclipse.plugin.cleancodesorter.task.Task;
import com.laboki.eclipse.plugin.cleancodesorter.task.TaskMutexRule;

public final class Sorter extends EventBusInstance {

	public static final String FAMILY = "JavaSorterPluginSorterTaskFamily";
	private static final TaskMutexRule RULE = new TaskMutexRule();
	private final Optional<IEditorPart> editor = EditorContext.getEditor();
	private static final Logger LOGGER =
		Logger.getLogger(Sorter.class.getName());

	@Subscribe
	@AllowConcurrentEvents
	public void
	eventHandler(final SortEvent event) {
		new Task() {


			@Override
			public void
			execute() {
				this.updateBuffer(this.getCompilationUnit());
			}

			private void
			updateBuffer(final ICompilationUnit unit) {
				try {
					this.tryToUpdateBuffer(unit);
				}
				catch (
					JavaModelException
					| MalformedTreeException
					| BadLocationException e) {
					Sorter.LOGGER.log(Level.SEVERE, e.getMessage(), e);
				}
			}

			private void
			tryToUpdateBuffer(final ICompilationUnit unit)
				throws JavaModelException,
					BadLocationException {
				final Document document = new Document(unit.getSource());
				this.applyEditsToDocument(unit, document);
				unit.getBuffer().setContents(document.get());
			}

			private void
			applyEditsToDocument(final ICompilationUnit unit, final Document document)
				throws BadLocationException,
					JavaModelException {
				new AstSorter(this.getContext(unit)).getTextEdit().apply(document,
					TextEdit.UPDATE_REGIONS);
			}

			private CompilationUnitContext
			getContext(final ICompilationUnit unit) {
				return new CompilationUnitContext(unit,
					AstContext.getAstNode(Sorter.this.editor).get());
			}

			private ICompilationUnit
			getCompilationUnit() {
				return AstContext.getCompilationUnit(Sorter.this.editor).get();
			}
		}.setFamily(Sorter.FAMILY).setRule(Sorter.RULE).start();
	}
}
