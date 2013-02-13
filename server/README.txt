This folder contains:

Server application files, such as enterprise and web
applications, database model and scripts, server tools etc.

Subfolders include

app  - contains all the application build modules / maven projects
db   - server database model file and scripts
docs - general server documentation files (such as manuals, installation
       instructions etc.)
	   
	   
NOTE: This folder would typically be used as a versioning root folder of the
server application, i.e. in a Subversion repository, you should move the
contents of this folder to a "trunk" subfolder and create "tags", "branches"
subfolders here. For MKS, you might want to make this folder a subproject.
