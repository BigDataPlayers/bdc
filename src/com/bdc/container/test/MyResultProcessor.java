package com.bdc.container.test;




import com.bdc.container.webservice.ResultProcessor;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: mkhanwalker
 * Date: 11/16/12
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyResultProcessor implements ResultProcessor {

    @Override
    public File getFile(String input) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getResult(String aid)
    {

       Result[] results = new Result[2];

       Result result1 = new Result();
        result1.setDate("11152012");
        result1.setBounce("50");
        result1.setSent("1000");
        result1.setOpen("500");
        result1.setClick("10");

        Result result2 = new Result();
        result2.setDate("11162012");
        result2.setBounce("60");
        result2.setSent("2000");
        result2.setOpen("600");
        result2.setClick("70");

        results[0] = result1;
        results[1] = result2;


        return MyResultProcessor.buildDocument(aid, results);

    }


    private static String buildDocument(String aid , Result[] results) {

        StringBuilder s = new StringBuilder();


        s.append("<?xml version=\"1.0\"?>");
        s.append("<Result>")  ;
        s.append("<aid>") ;
        s.append(aid);
        s.append("</aid>");
        s.append("<Items>" + results.length +"</Items>" )  ;
        s.append("<Days>");
        for (int i=0;i<results.length;i++)
        {
            Result result = results[i];
            s.append("<Item>") ;
            s.append("<Date>") ;
            s.append(result.getDate()) ;
            s.append("</Date>");
            s.append("<Sent>") ;
            s.append(result.getSent()) ;
            s.append("</Sent>") ;
            s.append("<Click>") ;
            s.append(result.getClick()) ;
            s.append("</Click>") ;
            s.append("<Open>") ;
            s.append(result.getOpen()) ;
            s.append("</Open>") ;
            s.append("<Bounce>") ;
            s.append(result.getBounce()) ;
            s.append("</Bounce>") ;
            s.append("</Item>") ;
        }
        s.append("</Days>") ;
        s.append("</Result>") ;

        return s.toString();

         /* DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();

        Document doc = implementation.createDocument("", "Result", null);

        Node root = doc.getDocumentElement();

        Element father = doc.createElement(FATHER);
        father.setAttribute(AGE, "49");
        father.appendChild(doc.createTextNode("M. Boyle"));
        root.appendChild(father);

        Element mother = doc.createElement(MOTHER);
        mother.setAttribute(AGE, "46");
        mother.appendChild(doc.createTextNode("M. A. Maclure"));
        root.appendChild(mother);

        Element children = doc.createElement(CHILDREN);
        root.appendChild(children);

        Element chloe = doc.createElement(CHILD);
        chloe.setAttribute(AGE, "12");
        chloe.appendChild(doc.createTextNode("C. Boyle"));
        children.appendChild(chloe);

        Element adrian = doc.createElement(CHILD);
        adrian.setAttribute(AGE, "10");
        adrian.appendChild(doc.createTextNode("A. Boyle"));
        children.appendChild(adrian);  */

      //  return doc;
    }


}
