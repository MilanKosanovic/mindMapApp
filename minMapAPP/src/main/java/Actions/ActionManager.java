package Actions;

public class ActionManager {
    AbstractAction exportAction;
    AbstractAction importAction;
    AbstractAction prettyAction;
    AbstractAction runAction;

    public ActionManager() {
        this.exportAction = new ExportAction();
        this.importAction = new ImportAction();
        this.prettyAction = new PrettyAction();
        this.runAction = new RunAction();
    }

    public AbstractAction getExportAction() {
        return exportAction;
    }

    public AbstractAction getImportAction() {
        return importAction;
    }

    public AbstractAction getPrettyAction() {
        return prettyAction;
    }

    public AbstractAction getRunAction() {
        return runAction;
    }
}
