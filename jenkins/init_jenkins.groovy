import jenkins.model.*
import hudson.security.*
import hudson.tasks.*
import jenkins.branch.*
import jenkins.plugins.git.*
import org.jenkinsci.plugins.workflow.multibranch.*

def instance = Jenkins.getInstance()

println "--> set Multi Branch Project"

WorkflowMultiBranchProject mp = instance.createProject(WorkflowMultiBranchProject.class, "petclinic");
mp.getSourcesList().add(new BranchSource(
	new GitSCMSource(null, "https://github.com/gerardborst/petclinic", "", "*", "", false), new DefaultBranchPropertyStrategy(new BranchProperty[0])));
mp.scheduleBuild2(0).getFuture().get()
