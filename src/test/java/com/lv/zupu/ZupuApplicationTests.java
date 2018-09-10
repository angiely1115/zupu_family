package com.lv.zupu;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lv.zupu.entity.FamilyVo;
import com.lv.zupu.mapperDao.FamilyDaoService;
import com.lv.zupu.mapperDao.FamilyMapperDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZupuApplicationTests {
    @Autowired
    private FamilyDaoService familyService;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

   @Test
    public void insertFamily() {
        FamilyVo familyVo = new FamilyVo();
        familyVo.setAuthorName("吕荣森");
        familyVo.setCoverImgUrl("kklll/d.jpg");
        familyVo.setCreateUserId("163987");
        familyVo.setDescribe("吕氏家族");
        familyVo.setFamilyName("吕氏");
        familyService.insert(familyVo);
        System.out.println(familyVo);
    }

    @Test
    public void getFamilyById() {
        FamilyVo familyVo = familyService.selectById(5);
        System.out.println(familyVo);
//      System.out.println(familyService.deleteById(familyVo.getId()));
    }

    @Test
    public void delFamilyById() {
        System.out.println(familyService.deleteById(5L));
    }

    @Test
    public void updateFamilyById() {
        FamilyVo familyVo = new FamilyVo();
        familyVo.setAuthorName("吕荣森");
        familyVo.setCoverImgUrl("kklll/d.jpg");
        familyVo.setCreateUserId("163987");
        familyVo.setDescribe("吕氏家族");
        familyVo.setFamilyName("吕氏1");
        familyVo.setId(3L);
        familyVo.setIsDelete(1);
        familyService.updateById(familyVo);
    }

    @Test
    public void updateFamilyByWrapper() {
        FamilyVo familyVo = new FamilyVo();
        familyVo.setAuthorName("吕荣森");
        familyVo.setCoverImgUrl("kklll/d.jpg");
        familyVo.setCreateUserId("163987");
        familyVo.setDescribe("吕氏家族");
        familyVo.setFamilyName("吕氏名册");
//        familyVo.setId(3L);
//        familyVo.setIsDelete(1);
        familyService.updateById(familyVo);
        EntityWrapper<FamilyVo> familyVoWrapper = new EntityWrapper<FamilyVo>();
        familyVoWrapper.eq("familyName", "吕氏1");
        familyService.update(familyVo, familyVoWrapper);
    }

    @Test
    public void selectByPage() {
        Page page = new Page();
        page.setCurrent(0);
        page.setSize(2);
//        Page<FamilyVo> familyVoPage = familyService.selectPage(page);
//        System.out.println(familyVoPage.getRecords());
//        familyVoPage = familyService.selectPage(page);
//        System.out.println("第二次查询：" + familyVoPage.getRecords());
        System.out.println(familyService.selectById(1));
        System.out.println(familyService.selectById(1));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        FamilyMapperDao familyMapperDao = sqlSession.getMapper(FamilyMapperDao.class);
      System.out.println(familyMapperDao.selectById(1));
        System.out.println(familyMapperDao.selectById(1));
    }


}
