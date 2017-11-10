package com.basic.interfaces;

/**
 * Created by ajay on 20/10/16.
 */

public interface RoomsSpaceCallback {
    void onsuccess(com.basic.models.response.roomspaceresponse.Entry roomSpaceRecord);
    void onfailure(String s);
}
