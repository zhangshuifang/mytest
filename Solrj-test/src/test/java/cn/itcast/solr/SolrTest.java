package cn.itcast.solr;

import cn.itcast.solr.pojo.Product;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

import java.util.List;

public class SolrTest {
    /*定义solrserver,用来操作solr*/
    private SolrServer solrServer= new
            HttpSolrServer("http://localhost:8080/solr/collection1");
    /*添加或者修改索引*/
    @Test
    public void saveOrUpdate() throws Exception {
        Product product=new Product();
        product.setPid("9527");
        product.setName("iphone8");
        product.setCatalogName("手机");
        product.setPrice(80000.0);
        product.setDescription("不错");
        product.setPicture("1.jpg");
        solrServer.addBean(product);
        solrServer.commit();
    }
    /*跟据id删除索引*/
    @Test
    public void deleteById() throws Exception {
        solrServer.deleteById("9527");
        solrServer.commit();
    }
    /*删除全部*/
    @Test
    public void deleteAll() throws Exception {
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }
    /*查询全部索引*/
    @Test
    public void query() throws Exception {
        //创建solrquery封装查询条件
        SolrQuery sq=new SolrQuery("*:*");
        //设置分页开始记录数
        sq.setStart(0);
        //设置每页显示记录数
        sq.setRows(10);
        //执行索引,得到创造性响应对象
        QueryResponse response = solrServer.query(sq);
        System.out.println("搜索得到总数量"+response.getResults().getNumFound());
        //获取搜索集合,并转化成实体集合
        List<Product> products = response.getBeans(Product.class);
        for (Product product : products) {
            System.out.println("------------分割线------------");
            System.out.println(product.getPid());
            System.out.println(product.getName());
            System.out.println(product.getCatalogName());
        }
    }
}
