//-----------------------------------------------------------------
// Listable.java
// Group 2
// Description: This interface defines methods to retrieve name, category, and ID information for listable items, typically used for user and device listings in a smart system.
// Created By:
// Edited By:
// Approved By: Braydon, Francisco, Liam
// Variables:
//
// Error Handling;
//
//
//
//-----------------------------------------------------------------

package messages.client;

public interface Listable {

    public abstract String getNameListable();
    // username - user/admin
    // device name - smart devices

    public abstract String getCategoryListable();
    // user/admin - user/admin
    // device type - smart device

    public abstract int getIDListable();
}
