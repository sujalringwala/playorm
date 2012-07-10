package com.alvazan.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alvazan.orm.api.base.AbstractBootstrap;
import com.alvazan.orm.api.base.DbTypeEnum;
import com.alvazan.orm.api.base.Index;
import com.alvazan.orm.api.base.NoSqlEntityManager;
import com.alvazan.orm.api.base.NoSqlEntityManagerFactory;
import com.alvazan.test.db.Activity;

public class TestIndexAndOrParens {

	private static NoSqlEntityManager mgr;
	private static Index<Activity> index;

	@BeforeClass
	public static void setup() {
		NoSqlEntityManagerFactory factory = AbstractBootstrap.create(DbTypeEnum.IN_MEMORY);
		factory.setup(null, "com.alvazan.test.db");
		mgr = factory.createEntityManager();
		index = setupRecords();
	}

	@Test
	public void testSimpleAnd() {
		List<Activity> findByName = Activity.findWithAnd(index, "hello", 5);
		Assert.assertEquals(1, findByName.size());
		
		List<Activity> list = Activity.findWithAnd(index, "hello", 6);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testSimpleOr() {
		List<Activity> findByName = Activity.findWithOr(index, "hello", 6);
		Assert.assertEquals(3, findByName.size());
		
		List<Activity> list = Activity.findWithOr(index, "nothaveThe5OrHellohere", 20);
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testParensVsNoParens() {
		//@NoSqlQuery(name="findWithParens", query="select * FROM TABLE e WHERE" +
		//		" e.name=:name and (e.numTimes = :numTimes or e.isCool = :isCool)"),	
		//@NoSqlQuery(name="findWithoutParens", query="select * FROM TABLE e WHERE" +
		//		" e.name=:name and e.numTimes = :numTimes or e.isCool = :isCool"),
		//We have a truth table of this where the result of A named query and B named query are on right side
		//We need to test #2 (A=F, B=T) and the opposite (A=T, B=F)
		//1 F, F, F : A=F, B=F
		//2 F, F, T : A=F, B=T
		//3 F, T, F : A=F, B=F
		//4 F, T, T : A=F, B=T
		//5 T, F, F : A=F, B=F
		//6 T, F, T : 
		//7 T, T, F : 
		//8 T, T, T :
		
		//First query should not be found....(A)
		List<Activity> withParens = Activity.findWithParens(index, "notfound", 99, 5.55f);
		Assert.assertEquals(0, withParens.size());
		
		//Second query should be found....(B)
		List<Activity> list = Activity.findWithoutParens(index, "notfound", 99, 5.55f);
		Assert.assertEquals(1, list.size());		
	}
	
	private static Index<Activity> setupRecords() {
		
		Activity act1 = new Activity();
		act1.setName("hello");
		act1.setMyFloat(5.65f);
		act1.setUniqueColumn("notunique");
		act1.setNumTimes(5);
		act1.setIsCool(true);
		mgr.put(act1);
		
		Activity act2 = new Activity();
		act2.setName("notelloBUTHas5ForNumTimes");
		act2.setMyFloat(5.65f);
		act2.setUniqueColumn("notunique");
		act2.setNumTimes(5);
		act2.setIsCool(true);
		mgr.put(act2);
		
		Activity act4 = new Activity();
		act4.setName("hello");
		act4.setMyFloat(5.65f);
		act4.setUniqueColumn("notunique");
		act4.setNumTimes(6);
		act4.setIsCool(true);
		mgr.put(act4);
		
		Activity act5 = new Activity();
		act5.setName("nothaveThe5OrHellohere");
		act5.setMyFloat(5.65f);
		act5.setUniqueColumn("notunique");
		act5.setNumTimes(6);
		act5.setIsCool(true);
		mgr.put(act5);		

		Activity act6 = new Activity();
		act6.setName("somethingtttt");
		act6.setMyFloat(5.55f);
		act6.setUniqueColumn("notunique");
		act6.setNumTimes(9);
		act6.setIsCool(true);
		mgr.put(act6);
		
		Index<Activity> index = mgr.getIndex(Activity.class, "/activity/byaccount/account1");
		index.addToIndex(act1);
		index.addToIndex(act2);
		index.addToIndex(act4);
		index.addToIndex(act5);
		index.addToIndex(act6);
		mgr.flush();
		return index;
	}

}