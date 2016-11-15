import jenkins.model.*
import hudson.security.*
import hudson.tasks.*
import jenkins.branch.*
import jenkins.plugins.git.*
import org.jenkinsci.plugins.workflow.multibranch.*

def instance = Jenkins.getInstance()

println "--> set Maven installation"
def extList = instance.getExtensionList(hudson.tasks.Maven.DescriptorImpl.class)[0];
def tmpList = (extList.installations as List);
tmpList.add(new hudson.tasks.Maven.MavenInstallation("Maven 3", "/opt/mvn", []));
extList.installations = tmpList
extList.save()

println "--> set Multi Branch Project"

WorkflowMultiBranchProject mp = instance.createProject(WorkflowMultiBranchProject.class, "petclinic");
mp.getSourcesList().add(new BranchSource(
	new GitSCMSource(null, "https://github.com/gerardborst/petclinic", "", "*", "", false), new DefaultBranchPropertyStrategy(new BranchProperty[0])));
mp.scheduleBuild2(0).getFuture().get()
