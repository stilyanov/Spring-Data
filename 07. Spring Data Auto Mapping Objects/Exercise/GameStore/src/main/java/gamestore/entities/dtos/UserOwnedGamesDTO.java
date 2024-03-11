package gamestore.entities.dtos;

public class UserOwnedGamesDTO {
    private String title;

    public UserOwnedGamesDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
