package Model;

/**
 * Created by Ahmed on 2/16/2018.
 */

public class Project {

    private String projectTitle;
    private String projectSupervisor;

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectSupervisor() {
        return projectSupervisor;
    }

    public void setProjectSupervisor(String projectSupervisor) {
        this.projectSupervisor = projectSupervisor;
    }

    public Project(String projectTitle, String projectSupervisor) {
        this.projectTitle = projectTitle;
        this.projectSupervisor = projectSupervisor;
    }
}
