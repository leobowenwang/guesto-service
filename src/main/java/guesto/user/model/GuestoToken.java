package guesto.user.model;

public class GuestoToken {
    private final String token;
    private final Long userId;
    private final String role;

    public GuestoToken(String token, Long userId, String role) {
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
