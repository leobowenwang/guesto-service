package guesto.user.model;

public class GuestoToken {
    private final String token;
    private final Long userId;

    public GuestoToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }
}
