This is an application template for TBNav, a utility to create Twitter Bootstrap styled navigation
bar with drop down menus from the output of Lift's Menu.builder. 

You can see a running example of the code here: [www.damianhelme.com/tbnav](www.damianhelme.com/tbnav)

For a more detailed write up of this code, see here: 

To download and run this application:

1. In your workspace directory, download and build the TBUtils library:

        git clone https://github.com/dph01/lift-TBUtils
        cd liftTBUtils
        ./sbt publish-local

2. In your workspace directory, download and run this application:
       
        git clone git://github.com/dph01/lift-TBNavbarTemplate.git
        cd lift-TBNavbarTemplate
        ./sbt ~container:start



