package model;

public class SessionManager {
	
	private static SessionManager instance;
	private String currentRol;
	private int currentID;
	
	public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    

    public String getCurrentUserRole() {
        return currentRol;
    }
    
    public void setCurrentUserRole(String currentRol) {
        this.currentRol = currentRol;
    }
    
    public int getCurrentEmpleadoId() {
        return currentID;
    }
    
    public void setCurrentEmpleadoId(int currentID) {
        this.currentID = currentID;
    }
    
    //String rol = SessionManager.getInstance().getCurrentUserRole();
    //int idEmpleado = SessionManager.getInstance().getCurrentEmpleadoId(); 

}
