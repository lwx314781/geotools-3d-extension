Þ          Ì               |     }       @   ¦     ç     û  7        H     N     S     Y     ]     b  Á   i  .   +  T  Z  W   ¯       4       C     È  û   ß  [  Û     7	     C	  @   `	     ¡	     µ	  7   Ê	     
     
     
     
     
     
     #
  (   ¿
  !  è
  F   
     Q  Ú   Z  ^   5       Ä   ¢   $ cd ../../ $ cd unsupported/geometry-ng $ git clone https://github.com/STEMLab/geotools-3d-extension.git $ mvn clean install $ sudo ./cppbuild.sh $ sudo apt-get install -y cmake libgmp3-dev libmpfr-dev Boost CGAL CMAKE GMP MPFR SFCGAL SFCGAL, CGAL, Boost ë¼ì´ë¸ë¬ë¦¬ìì íìí ë¼ì´ë¸ë¬ë¦¬ë¤ì ì¤ì¹í©ëë¤. ì´ë¯¸ ì´ ë¼ì´ë¸ë¬ë¦¬ë¤ì´ ì¤ì¹ëì´ ìë¤ë©´ ë¤ìì ê³¼ì ì ìëµí´ë ì¢ìµëë¤. Ubuntu 16.04ìì GeoTools 3Dë¥¼ ì¤ì¹íê¸° gt-geometry-ng ëª¨ë ë´ì cppbuild.sh íì¼ì ì¤ííë©´ SFCGAL, CGAL, Boost ë¼ì´ë¸ë¬ë¦¬ê° ìì¤íì ìëì¼ë¡ ì¤ì¹ë©ëë¤. ìì¤íì í´ë¹ ë¼ì´ë¸ë¬ë¦¬ë¤ì ì¤ì¹íê¸° ìíì¬ gt-geometry-ng ëª¨ëì ê²½ë¡ë¡ ì´ëí í sudo ê¶í ìì²­ê³¼ í¨ê» cppbuild.shë¥¼ ë¤ìê³¼ ê°ì´ ì¤ííì­ìì¤. ë¨¼ì  ë¤ìì ëªë ¹ì´ë¡ Git Repositoryë¡ ë¶í° íë¡ì í¸ë¥¼ ë°ììµëë¤. ë¹ë ì´ ì¥ììë ì´ íë¡ì í¸ë¥¼ ë¹ëíê¸° ìí ë°©ë²ì ìê°í©ëë¤. ì´ íë¡ì í¸ê° ì¬ì©íë ëª ê°ì§ ë¼ì´ë¸ë¬ë¦¬ê° c++ ì¸ì´ ê¸°ë°ì´ê¸° ëë¬¸ì ì´ íë¡ì í¸ë¥¼ ë¹ëíê¸° ì´ì ì íì ë¼ì´ë¸ë¬ë¦¬ë¤ì ì°ì ì ì¼ë¡ ì¤ì¹íë ê³¼ì ì´ íìí©ëë¤. ì´ì  ë¤ìê³¼ ê°ì´ ë£¨í¸ ê²½ë¡ë¡ ëìê°ì ì ì²´ íë¡ì í¸ë¥¼ ë¹ëíì¬ ë¹ëê° ì±ê³µí¨ì íì¸íì­ìì¤. íì ë¼ì´ë¸ë¬ë¦¬ íì ë¼ì´ë¸ë¬ë¦¬ê° ëª¨ë ì ëë¡ ì¤ì¹ëìëì§ íì¸íê¸° ìí´ gt-geometry-ng ëª¨ëì ë¤ìê³¼ ê°ì´ ë¹ëí©ëë¤. ëª¨ë  ë¹ëê° ìë£ë í ìíëë ëª¨ë  íì¤í¸ê° ì±ê³µíë©´ ì¤ì¹ê° ìë£ë ê²ìëë¤. Project-Id-Version: geotools-3d-extension 2.7.1
Report-Msgid-Bugs-To: 
POT-Creation-Date: 2017-08-28 10:27-0400
PO-Revision-Date: YEAR-MO-DA HO:MI+ZONE
Last-Translator: FULL NAME <EMAIL@ADDRESS>
Language-Team: LANGUAGE <LL@li.org>
MIME-Version: 1.0
Content-Type: text/plain; charset=utf-8
Content-Transfer-Encoding: 8bit
Generated-By: Babel 2.4.0
 $ cd ../../ $ cd unsupported/geometry-ng $ git clone https://github.com/STEMLab/geotools-3d-extension.git $ mvn clean install $ sudo ./cppbuild.sh $ sudo apt-get install -y cmake libgmp3-dev libmpfr-dev Boost CGAL CMAKE GMP MPFR SFCGAL Install the required libraries from the SFCGAL, CGAL, and Boost libraries. If you already have these libraries installed, you can skip the following steps. GeoTools 3D Installation on Ubuntu 16.04 Running the cppbuild.sh file in the gt-geometry-ng module automatically installs the SFCGAL, CGAL, and Boost libraries on your system. To install the appropriate libraries on your system, go to the gt-geometry-ng module path and run cppbuild.sh with the sudo permission request as follows: First, get the project from Git Repository with the following command. Building This chapter will show you how to build this project. Since some of the libraries used by this project are based on the c ++ language, it is necessary to prioritize the necessary libraries before building this project. Now return to the root path and build the entire project to make sure the build is successful: Prerequisites To verify that all required libraries are installed correctly, build the gt-geometry-ng module as follows: If all the tests performed after all builds are successful, the installation is complete. 