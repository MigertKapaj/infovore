package com.ontology2.centipede.jena;
import static org.junit.Assert.*;

import java.net.MalformedURLException;

import org.junit.Test;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class JenaNodeTester {
    @Test
    public void testParenthesis() {
        Node n=Node.createURI("http://theyknow.whatiswhat.com/text(withParens)");
        String uri=n.toString();
        assertEquals("http://theyknow.whatiswhat.com/text(withParens)",uri);
    }

    @Test
    public void testAngleBracket() {
        Node n=Node.createURI("http://theyknow.whatiswhat.com/text<withAngleBrackets>");
        String uri=n.toString();
        assertEquals("http://theyknow.whatiswhat.com/text<withAngleBrackets>",uri);
    }

    @Test
    public void testQueryParsing() {
        Query q=QueryFactory.create(
                "PREFIX basekb: <http://rdf.basekb.com/ns/> \n" +
                        "\n" +
                        "SELECT * {basekb:en.brad_pitt ?p ?o .}"
                );
        assertTrue(true);
    }

    //	@Test
    public void testBinding() {
        Query q=QueryFactory.create(
                "SELECT ?dayName WHERE {"+ 
                        "   FILTER (!bound(?dayIDCheck))"+
                        "}"+
                        "BINDINGS ?dayIDCheck ?dayName {"+
                        "   (0 'Sunday'@en)"+
                        "   (1 'Monday'@en)"+
                        "   (2 'Tuesday'@en)"+
                        "   (3 'Wednesday'@en)"+
                        "   (4 'Thursday'@en)"+
                        "   (5 'Friday'@en)"+
                        "   (6 'Saturday'@en)" +
                        "}"
                );
        Model m=ModelFactory.createDefaultModel();
        QueryExecution qe=QueryExecutionFactory.create(q,m);
        ResultSet r=qe.execSelect();
        assertEquals(1,r.getResultVars().size());
        assertEquals("dayName",r.getResultVars().get(0));
        assertTrue(r.hasNext());
        QuerySolution qs=r.nextSolution();
        assertEquals("Sunday",qs.get("dayName").asLiteral().getLexicalForm());	
    }
}
