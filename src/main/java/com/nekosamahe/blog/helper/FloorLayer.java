package com.nekosamahe.blog.helper;

import com.nekosamahe.blog.helper.*;

import java.util.*;

public class FloorLayer {
    private Master master;
    private List<Replier> replies;
    
    public FloorLayer() {}

    public FloorLayer(Master master, List<Replier> replies) {
        this.master = master;
        this.replies = replies;
    }

    public Master getMaster() {
        return master;
    }

    public List<Replier> getReplies() {
        return replies;
    }

    public void setMaster(Master master) {
        this.master = master;
    }
    
    public void setReplies(List<Replier> replies) {
        this.replies = replies;
    }
}