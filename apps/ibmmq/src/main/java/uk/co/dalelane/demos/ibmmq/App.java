package uk.co.dalelane.demos.ibmmq;

import javax.jms.Connection;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.JMSException;

public abstract class App {

    protected static Connection createConnection() throws JMSException {
        JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
        JmsConnectionFactory cf = ff.createConnectionFactory();
        cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, getStringEnv("MQ_HOST"));
        cf.setIntProperty(WMQConstants.WMQ_PORT, getIntEnv("MQ_PORT"));
        cf.setStringProperty(WMQConstants.WMQ_CHANNEL, getStringEnv("MQ_CHANNEL"));
        cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, getStringEnv("MQ_QMGR"));
        cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, false);
        return cf.createConnection();
    }

    protected static String getStringEnv(String envvar) throws JMSException {
        String envvalue = System.getenv(envvar);
        if (envvalue == null) {
            throw new JMSException("Missing required environment variable " + envvar);
        }
        System.out.println("Using " + envvar + " value >" + envvalue + "<");
        return envvalue;
    }

    protected static int getIntEnv(String envvar) throws JMSException {
        String envvalue = getStringEnv(envvar);
        return Integer.parseInt(envvalue);
    }
}
