package dao.hibernate;

import dao.ClientDao;
import models.Client;

public class ClientDaoImpl extends GenericDaoImpl<Client, Integer> implements ClientDao{

    @Override
    public Client findByUsernameAndPassword(String username, String password) {
        return null;
    }
}
