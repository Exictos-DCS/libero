package com.exictos.devops.services.managers

import com.exictos.devops.helpers.LiberoHelper
import com.exictos.devops.helpers.NssmWrapper
import com.exictos.devops.profiles.Instance
import com.exictos.devops.services.Service

abstract class ServiceManager {



    /**
     * Installs service
     *
     * @param service
     * @return true if service was installed successfully
     */
    abstract protected boolean install(String pathToPackage, String installDirectory, String serviceName, String binPath, String argument)

    /**
     * Starts service
     *
     * @param service
     * @return true if service was started successfully
     */
    abstract boolean start(Service service)

    /**
     * Stops service
     *
     * @param service
     * @return true if service was stopped successfully
     */
    abstract boolean stop(Service service)

    /**
     * Removes service
     *
     * @param service
     * @return true if service was removed successfully
     */
    abstract boolean remove(Service service)

    /**
     * Gets the status of service
     *
     * @param service
     * @return the status of service
     */
    abstract NssmWrapper.Status status(Service service)

    /**
     * Lists all instances of service installed in the directory
     *
     * @param service
     * @return a list of instances of service installed
     */
    static List<Instance> listInstances(Service service)
    {
        List<Instance> instances = new ArrayList<Instance>()
        String instancePrefix = LiberoHelper.extractFolderNameFromPackageFile(service._package)

        service.installDirectory.eachDir {directory ->
            if(directory.getName().startsWith(instancePrefix)){
                Instance instance = new Instance()
                instance.setName(directory.getName())
                instance.setTimestamp(LiberoHelper.extractTimestamp(directory.getName()))
                instances.add(instance)
            }
        }

        LiberoHelper.oldnessLevel(instances)
    }

    /**
     * Install service with rollback procedure
     *
     * @param service
     */
    void installServiceWithRollback(Service service)
    {
        stop(service)
        uninstallOldInstances(service)
        installService(service)
    }

    /**
     * Installs service
     *
     * @param service
     */
    abstract protected void installService(Service service)

    /**
    * Uninstall all old instances of service with an oldness level > oldnessThreshold
    *
    * @param service
    * @param oldnessThreshold
    */
    static void uninstallOldInstances(Service service, int oldnessThreshold = 0)
    {
        listInstances(service).each {instance ->
            if(instance.getOldness() > oldnessThreshold)
                new File(service.installDirectory,instance.getName()).deleteDir()
        }
    }

}
