import javax.xml.xpath.*
import javax.xml.parsers.DocumentBuilderFactory


def script = new GroovyScriptEngine( '.' ).with {
    loadScriptByName( '../../Logger.groovy' ) //for this to work, Logger.groovy should've already been put inside platform directory
}
this.metaClass.mixin script


def checkLocalExtensionXml(boolean shouldExist, String... extNames){
    def fis =null;
    try{
        def xpath = XPathFactory.newInstance().newXPath()
        def builder     = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        fis = new FileInputStream(new File(System.getProperty("HYBRIS_CONFIG_DIR"),"localextensions.xml"))  
        def localextensionsXml     = builder.parse(fis).documentElement
    
        addLog('Checking localextensions.xml...');
        for(def extName:extNames){
            //note: ^ is the xor operator.  It's being used as follows: if left operand is true, it essentially inverts the right operand (  i.e., false ^ b = b     true ^ b = !b  )
            if(  !shouldExist ^  ('true'.equals(xpath.evaluate("/hybrisconfig/extensions/extension/@name=\'"+extName+"\'", localextensionsXml))  || xpath.evaluate("/hybrisconfig/extensions/extension[contains(@dir,'"+extName+"')]", localextensionsXml,XPathConstants.NODE)!=null)    ){
                addLog('...checking that extension name ' + extName + ' IS ' + ( (shouldExist)?'':'NOT ' ) + 'present.....OK')
            }else{
                addError('Please ' + ( (shouldExist)?"add":"remove" ) + ' <extension name=\"'+extName+'\"/> ' + ( (shouldExist)?'to':'from' ) + ' your localextensions.xml, rebuild the platform, and restart the server')
            }
        }
    
    }finally{
        if(fis!=null)
        fis.close()
    }   
}

def checkLocalProperties(valuesMap){
    Properties properties = new Properties()
    File propertiesFile = new File(System.getProperty("HYBRIS_CONFIG_DIR"),"local.properties")
    propertiesFile.withInputStream {
        properties.load(it)
    }
  for(entry in valuesMap){
    if(properties."${(entry.key)}" == "${entry.value}"){
        addLog("Checking property ${entry.key} ....OK")
    }else{
        addError("Please set property ${entry.key}=${entry.value}")
    }
  }
}

checkLocalExtensionXml(true, "bookstorefulfilmentprocess","bookstorecore","bookstoreinitialdata","bookstorefacades","bookstorestorefront","bookstorecockpits")

checkLocalExtensionXml(false, "bookstoretest");

checkLocalProperties(['hac.webroot':"/hac",'website.bookstore.http':"http://bookstore:9001",'website.bookstore.https':"https://bookstore:9002"])

printOutputLog()