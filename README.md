
# A Simple Configuration System for Java

Example usage.  Suppose you have an `application.properties` file in your working directory that contains

    abc.def=ghi
    foo.bar=123
        
and an environment variable `FOO_BAR` with value 456, then you can execute the following:         

    SimpleConfig simpleConfig = new SimpleConfig()
        .load("application.properties")
        .load(System.getenv())
        ;
        
    simpleConfig.get("abc.def"); // returns "ghi"
    simpleConfig.get("foo.bar"); // returns "456", overridden by env variable
    
Then suppose you have secrets in a secrets service like Hashicorp Vault.  You want to retrieve secrets from Vault
and add them to the set of properties; but in order to do so, you want to **configure** the url of Vault, and be
able to configure it statically in a properties file, and override it with an environment value.

Now you're in a bind: you need to use the configuration to obtain further configuration.  We got you covered: all
you have to do is implement a function that will take the existing configuration, and use it to retrieve further 
configuration.  I call it "refining" the configuration.

    Function<ConfigSource, Map<String, String>> refinement = existingConfig -> ... use existingConfig to access e.g. Vault;
    simpleConfig.load(refinement); 
