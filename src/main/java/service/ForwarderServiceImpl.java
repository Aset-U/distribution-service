package service;


import dao.ForwarderDao;
import dao.hibernate.ForwarderDaoImpl;
import models.Forwarder;

public class ForwarderServiceImpl extends GenericServiceImpl<Forwarder, Integer> implements ForwarderService{

    private ForwarderDao forwarderDao;

    public ForwarderServiceImpl() {
        this.forwarderDao = new ForwarderDaoImpl();
    }

    public Forwarder getByUsernameAndPassword(String username, String password) {
        forwarderDao.openCurrentSession();
        Forwarder forwarder = forwarderDao.findByUsernameAndPassword(username, password);
        forwarderDao.closeCurrentSession();
        return forwarder;
    }
}
