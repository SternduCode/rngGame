package rngGame.main;

import java.util.*;

import javafx.scene.input.KeyCode;

// TODO: Auto-generated Javadoc
/**
 * The Class UndoRedo.
 */
public class UndoRedo {

	/**
	 * The Interface UndoRedoAction.
	 */
	public interface UndoRedoAction {

		/**
		 * Redo.
		 */
		void redo();

		/**
		 * Undo.
		 */
		void undo();
	}

	/**
	 * The Class UndoRedoActionBase.
	 */
	public static class UndoRedoActionBase implements UndoRedoAction {

		/** The redo. */
		private final Runnable undo, redo;

		/**
		 * Instantiates a new undo redo action base.
		 *
		 * @param undo the undo
		 * @param redo the redo
		 */
		public UndoRedoActionBase(Runnable undo, Runnable redo) {
			this.undo = undo;
			this.redo = redo;
		}

		/**
		 * Redo.
		 */
		@Override
		public void redo() {
			redo.run();
		}

		/**
		 * Undo.
		 */
		@Override
		public void undo() {
			undo.run();
		}

	}

	/** The instance. */
	private static UndoRedo INSTANCE;

	/** The actions. */
	private final List<UndoRedoAction> actions;

	/** The position. */
	private int position;

	/**
	 * Instantiates a new undo redo.
	 */
	private UndoRedo() {
		actions = new ArrayList<>();
		position = -1;
		Input.getInstance(null).setKeyHandler("Undo", mod -> {
			if (mod.isControlPressed()) undo();
		}, KeyCode.Z, false);
		Input.getInstance(null).setKeyHandler("Redo", mod -> {
			if (mod.isControlPressed()) redo();
		}, KeyCode.Y, false);
	}

	/**
	 * Gets the single instance of UndoRedo.
	 *
	 * @return single instance of UndoRedo
	 */
	public static UndoRedo getInstance() { return INSTANCE != null ? INSTANCE : (INSTANCE = new UndoRedo()); }

	/**
	 * Adds the action.
	 *
	 * @param action the action
	 */
	public void addAction(UndoRedoAction action) {
		if (position<actions.size()-1)
			actions.removeIf(ac -> (actions.indexOf(ac)>position));
		actions.add(action);
		redo();
	}

	/**
	 * Clear actions.
	 */
	public void clearActions() {
		actions.clear();
		position = -1;
	}

	/**
	 * Redo.
	 */
	public void redo() {// CTRL+Y
		if (position < actions.size() - 1) {
			position++;
			actions.get(position).redo();
		}
	}

	/**
	 * Undo.
	 */
	public void undo() {//CTRL+Z
		if (position > -1) {
			actions.get(position).undo();
			position--;
		}
	}

}
