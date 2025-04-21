package vector.TaskSync.models;



import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("manager:read"),
    MANAGER_DELETE("manager:delete"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create");

    @Getter
    private final String permission;

}


