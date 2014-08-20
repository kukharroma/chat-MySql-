package com.chat.dao.impl;


import com.chat.dao.IMessageDAO;
import com.chat.model.Message;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Provides methods that allow you to interact with messaging
 * 
 */
@Repository("messageDAO")
public class MessageDAO implements IMessageDAO {

    private static final Logger log = Logger.getLogger(MessageDAO.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Saves a message
     * @param message message you want to save
     */
    @Override
    public void save(Message message) {
        sessionFactory.getCurrentSession().save(message);
    }

    /**
     *
     * @return list of messages
     */
    @Override
    public List<Message> getLasHundredMessages() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from message order by id desc LIMIT 100").addEntity(Message.class);
        return query.list();
    }

    /**
     *
     * @return list of messages
     */
    @Override
    public List<Message> getAllMessages() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from message").addEntity(Message.class);
        return query.list();
    }

    /**
     *
     * @param dateFrom - time from which you want to get messages
     * @return list of messages
     */
    @Override
    public List<Message> getMessagesFromSecond(String dateFrom) {
        long longDate = Long.valueOf(dateFrom);
        Date date = new Date(longDate);
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select * from message where message.date > :date ").addEntity(Message.class);
        query.setParameter("date", date);
        return query.list();
    }

    /**
     * Delete all messages from database
     */
    @Override
    public void deleteAllMessages() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from message");
        query.executeUpdate();
    }
}
