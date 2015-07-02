# DRIC README File

### Publicly-available URL  
[http://dric-public-449159857.us-east-1.elb.amazonaws.com:8080/dric/] (http://dric-public-449159857.us-east-1.elb.amazonaws.com:8080/dric/)  

### Development Approach 
Dev Technology Group utilized our established process for providing Agile development services for a variety of Federal Government customers to develop the Drug Recall Information Center (DRIC) application. Leveraging key tenants of the US Digital Services Playbook, we developed an open source prototype to provide current drug recall information for consumers. DRIC enables users to easily search for reports of recalled drugs and see a visual image of select recalled drugs. We adopted a strategy of “mobile first” development, ensuring that the application runs on both a traditional desktop and mobile devices.  

Michelle Scheuerman was the **leader** for this project and actively engaged in each step of the development to ensure delivery of a quality product and championed our agile process. She initiated the project with a kick-off meeting to share the vision for DRIC and the process to be followed. Our **collaborative and multidisciplinary team** included Adam D’Angelo (Technical Architect), James Caple (Frontend Web Developer), Jesse Hess (Backend Web Developer), and Larry Cutlip-Mason (DevOps Engineer) and provided the needed skill sets and experience to successfully develop the DRIC application. Working together in our Dev Technology collaboration space, the self-organized team completed prioritized tasks from the Kanban board during a series of development iterations. *(criteria a,  b, & j)*  

Our architectural platform includes 15 **modern, open source technologies**, including Apache HttpComponents, RESTEasy, JQuery, Bootstrap, Google Gson and others. The application was developed using Dev Technology’s **Infrastructure as a Service (IaaS)** environment hosted by Amazon Web Services (AWS). Our standard development approach included writing and executing unit tests to provide automated test coverage; we leveraged JUnit for the back end and Selenium for the front end to provide extensive testing coverage of the DRIC application. The team also used WAVE (Web Accessibility Evaluation Tool) to ensure compliance with Section 508 requirements. *(criteria c, d, & e)*  

We used Jenkins to provide a **continuous integration** environment and enable the automated building and testing of code after it’s committed to version control. Immediate test results allowed developers to quickly resolve any issues and launch a new build. Our **configuration management** solution was GitHub, enabling the team to take advantage of the benefits of a distributed version control system. Amazon CloudWatch provides **continuous monitoring** of our AWS IaaS environment; the development team receives an email alert whenever there was an issue with the hosting environment. Docker was used to support the **deployment of the code within a container** enabling the application to always run the same, regardless of the environment. The team used the drugs enforcement reports API from openFDA, as well as the RxImageAccess API from the National Library of Medicine (NLM) to provide images as a second data source. We developed a RESTful service as an API to our frontend, which integrates with the external APIs to consume relevant data. *(criteria  f, g, h & i)*  

Our **iterative development** approach started with the leader (product owner) sharing her vision for the application with the development team. During the initial collaboration session, the team discussed available data and how it could be used at a detailed level. Initial user stories were written and the Jira Kanban board was populated, which the team used throughout the process to track the status of tasks and record notes. An initial task was developing a set of wireframes, which were revised throughout the Agile development process. The team held stand-ups twice a day, although – because they were co-located in the same room – communication was constant. *(criteria j)*  

The team also held frequent reviews with the leader, so she could see the progress of the DRIC application. Tasks from the reviews were logged in Jira and worked according the priority established by the leader. Informal reviews were also conducted, as the URL was available to the project team and frequent virtual checkpoints were held, especially to review the frontend design and layout.  

The [installation guide](https://github.com/DevTechnology/DRIC/blob/master/INSTALLATION%20INSTRUCTIONS.md) is included in the root of the DRIC GitHub repository to enable users to successfully install it in any environment. To install DRIC on your local Linux Fedora instance, follow the [Docker Installation](https://github.com/DevTechnology/DRIC/blob/master/Docker/readme.md) readme. If you wish to build and deploy DRIC, please follow the [Maven Build & Deploy](https://github.com/DevTechnology/DRIC/blob/master/api/dric-api-webapp/readme.md) readme. For information regarding manually deploying the war to the container, please see the [Manual War Deployment](https://github.com/DevTechnology/DRIC/blob/master/Documentation/Configuration/ManualWarInstall.md) readme.  

In addition, the prototype is **openly licensed** and is offered **free of charge** by Dev Technology Group. *(criteria k & l)*
