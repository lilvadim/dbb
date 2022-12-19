package ru.nsu.dbb.driver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.FileUtils;

public class JdbcDrivers {

    private static class DriverDescription {

        @XmlAttribute
        private String name;

        @XmlAttribute
        private String subprotocol;

        @XmlAttribute(name = "url")
        private URL downloadUrl;

    }

    @XmlRootElement(name = "drivers")
    private static class DriverList {

        @XmlElement(name = "driver")
        private List<DriverDescription> driverDescriptions = new ArrayList<>();

    }

    private static final URL driverListUrl = Driver.class.getResource("/ru/nsu/dbb/jdbc-drivers.xml");
    private static final File driverDirectory = new File("drivers");
    private static DriverList driverList = new DriverList();

    static {
        try {
            JAXBContext context = JAXBContext.newInstance(DriverList.class);
            Unmarshaller driverListReader = context.createUnmarshaller();
            driverList = (DriverList) driverListReader.unmarshal(driverListUrl);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private JdbcDrivers() {}

    public static boolean isDriverSupported(String databaseUrl) {
        return getDriverDescription(databaseUrl) != null;
    }

    public static boolean isDriverAvailable(String databaseUrl) {
        DriverDescription desc = getDriverDescription(databaseUrl);
        return desc != null && Files.exists(getDriverJarFile(desc).toPath());
    }

    public static java.sql.Driver getDriver(String databaseUrl) throws SQLException {
        DriverDescription desc = getDriverDescription(databaseUrl);
        if (desc == null) {
            throw new SQLException(String.format("No suitable driver found for %s", databaseUrl));
        } else {
            try {
                File driverJarFile = downloadDriver(desc);
                URLClassLoader classLoader = new URLClassLoader(new URL[] { driverJarFile.toURI().toURL() });
                return ServiceLoader.load(java.sql.Driver.class, classLoader).iterator().next();
            } catch (IOException e) {
                throw new SQLException("Could not load suitable driver", e);
            } catch (NoSuchElementException e) {
                throw new SQLException("Driver class not found in downloaded JAR");
            }
        }
    }

    private static File downloadDriver(DriverDescription desc) throws IOException {
        File driverJarFile = getDriverJarFile(desc);
        if (Files.notExists(driverJarFile.toPath())) {
            FileUtils.copyURLToFile(desc.downloadUrl, driverJarFile, 30000, 30000);
        }
        return driverJarFile;
    }

    private static File getDriverJarFile(DriverDescription desc) {
        return new File(driverDirectory, desc.name);
    }

    private static DriverDescription getDriverDescription(String databaseUrl) {
        for (DriverDescription desc : driverList.driverDescriptions) {
            if (databaseUrl.startsWith(desc.subprotocol)) {
                return desc;
            }
        }
        return null;
    }

}
