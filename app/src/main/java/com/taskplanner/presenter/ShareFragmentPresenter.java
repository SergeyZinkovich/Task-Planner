package com.taskplanner.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.taskplanner.EventModel;
import com.taskplanner.data.PermissionManager;
import com.taskplanner.data.entity.PermissionRequestEntity;
import com.taskplanner.ui.ShareFragmentView;

import java.util.ArrayList;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class ShareFragmentPresenter extends MvpPresenter<ShareFragmentView>
        implements PermissionManager.createPermissionCallback {

    private Router router;

    private EventModel event;

    private final String[] actions = new String[]{"READ", "UPDATE", "DELETE"};

    public ShareFragmentPresenter(Router router, EventModel event) {
        this.router = router;
        this.event = event;
    }

    public EventModel getEvent() {
        return event;
    }

    public void createShareToken(boolean[] actions){
        ArrayList<PermissionRequestEntity> permissions = new ArrayList<>();
        for (int i = 0; i < 3 ; i++){
            if (actions[i]){
                PermissionRequestEntity eventPermission = new PermissionRequestEntity();
                PermissionRequestEntity patternPermission = new PermissionRequestEntity();
                eventPermission.setEntityId(event == null? null : event.getId());
                patternPermission.setEntityId(event == null? null : event.getPatternId());
                eventPermission.setEntityType("EVENT");
                patternPermission.setEntityType("PATTERN");
                eventPermission.setAction(this.actions[i]);
                patternPermission.setAction(this.actions[i]);
                permissions.add(eventPermission);
                permissions.add(patternPermission);
            }
        }
        PermissionRequestEntity[] req = new PermissionRequestEntity[permissions.size()];
        permissions.toArray(req);
        PermissionManager.getInstance().createPermission(req, this);
    }

    @Override
    public void setPermissionToken(String token) {
        getViewState().setToken(token);
    }
}
