package com.tim.one.repository


import net.sf.ehcache.CacheManager
import net.sf.ehcache.config.DiskStoreConfiguration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Shared
import spock.lang.Specification

import com.tim.one.model.BankCode
import com.tim.one.model.Project
import com.tim.one.model.ProjectCategory
import com.tim.one.model.ProjectPhoto
import com.tim.one.model.ProjectSoundcloud
import com.tim.one.model.ProjectTag
import com.tim.one.model.ProjectVideo
import com.tim.one.model.User

/**
 * @author neodevelop
 * NOTE: You should enable in the logger
 * org.hibernate.stat=DEBUG
 * To see the performance
 *
 */
@ContextConfiguration(locations=["classpath:/persistence-appctx.xml","classpath:/cache-appctx.xml"])
class UseCacheIntegrationSpec extends Specification {
  
  @Autowired
  BankCodeRepository bankCodeRepository
  
  @Autowired
  ProjectCategoryRepository projectCategoryRepository
  
  @Shared
  def classesInCache = [BankCode, Project, ProjectCategory, ProjectPhoto, ProjectSoundcloud, ProjectTag, ProjectVideo, User]

  void "Should use cache instead of execute query when we use conventional methods"(){
    given:
    List aList = null
    File f = new File("${System.getProperty('java.io.tmpdir')}")
    when:
    10.times{ aList = bankCodeRepository.findAll() }
    def cacheFilenames = f.listFiles({ dir,currentFile -> 
      currentFile ==~ /.*?\.(data|index)/ 
    } as FilenameFilter)*.name*.replaceAll(/\.data|\.index/,"")
    then:
    aList
    classesInCache*.name.every { cic ->
      cacheFilenames.contains cic
    }
  }
  
  void "Should use cache instead of execute query when we use specific methods"(){
    given:
    List aList = null
    when:
    5.times {
      aList = projectCategoryRepository.findCategories()
      aList.each { c ->
        5.times { projectCategoryRepository.findSubCategories(c.id) }
      }
    }
    then:
    aList
  }
}
