# Dockerfile

FROM ubuntu
MAINTAINER mariusz.szymanski@merapar.com

# Install wget & JRE
RUN apt-get clean && \
	apt-get update && \
	apt-get -qy install \
			wget \
			default-jre-headless \
			telnet \
			iputils-ping \
			unzip

# Install jmeter
RUN   mkdir /jmeter \
	&& cd /jmeter/ \
	&& wget https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-3.3.tgz \
	&& tar -xzf apache-jmeter-3.3.tgz \
	&& rm apache-jmeter-3.3.tgz \
	&& mkdir /jmeter-plugins \
	&& cd /jmeter-plugins/ \
	&& wget https://jmeter-plugins.org/downloads/file/JMeterPlugins-ExtrasLibs-1.4.0.zip \
	&& unzip -o JMeterPlugins-ExtrasLibs-1.4.0.zip -d /jmeter/apache-jmeter-3.3/

# Set Jmeter Home
ENV JMETER_HOME /jmeter/apache-jmeter-3.3/

# Add Jmeter to the Path
ENV PATH $JMETER_HOME/bin:$PATH

# Copy images & test
COPY images /test/images
COPY load_test_1.jmx /test/
COPY load_test_2.jmx /test/
COPY load-test-1.0.war /test/

WORKDIR /test/

CMD set JVM_ARGS="-Xms1024m -Xmx1024m -Dpropname=propvalue"
# CMD jmeter -n -t load_test_1.jmx -l load-test-results.jtl
CMD java -jar load-test-1.0.war
