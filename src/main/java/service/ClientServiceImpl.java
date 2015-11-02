package service;


import dao.ClientDao;
import dao.hibernate.ClientDaoImpl;
import models.Client;

public class ClientServiceImpl extends GenericServiceImpl<Client, Integer> implements ClientService {

    private ClientDao clientDao;

    public ClientServiceImpl() {
        this.clientDao = new ClientDaoImpl();
    }

    public Client getByUsernameAndPassword(String username, String password) {
        clientDao.openCurrentSession();
        Client client = clientDao.findByUsernameAndPassword(username, password);
        clientDao.closeCurrentSession();
        return client;
    }
}
