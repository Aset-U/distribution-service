package service;


import models.Admin;
import models.Client;
import models.Forwarder;
import models.User;

public class UserSearchServiceImpl implements UserSearchService {

    public User getByUsernameAndPassword(String username, String password) {

        ClientService clientService = new ClientServiceImpl();
        Client client = clientService.getByUsernameAndPassword(username, password);

        ForwarderService forwarderService = new ForwarderServiceImpl();
        Forwarder forwarder = forwarderService.getByUsernameAndPassword(username, password);

        AdminService adminService = new AdminServiceImpl();
        Admin admin = adminService.getByUsernameAndPassword(username, password);

        if (client != null) {
            return client;
        } else if (forwarder != null) {
            return forwarder;
        } else if (admin != null) {
            return admin;
        }

        return null;
    }
}
