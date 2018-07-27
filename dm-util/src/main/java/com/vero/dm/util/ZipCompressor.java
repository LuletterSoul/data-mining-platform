package com.vero.dm.util;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:02 2018/7/26.
 * @since data-mining-platform
 */

@Slf4j
public class ZipCompressor
{
    private String zipFileName; // 目的地Zip文件

    private String sourceFileName; // 源文件（带压缩的文件或文件夹）

    public ZipCompressor(String zipFileName, String sourceFileName) {
        this.zipFileName = zipFileName;
        this.sourceFileName = sourceFileName;
    }

    public void zip(List<String> specialPaths)
        throws Exception
    {
        // File zipFile = new File(zipFileName);
        log.debug("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊----压缩开始----＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
        // 创建zip输出流
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

        // 创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);

        File sourceFile = new File(sourceFileName);

        // 调用函数
        compress(out, bos, sourceFile, sourceFile.getName(),specialPaths);
        bos.close();
        out.close();
        log.debug("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊----压缩成功----＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
    }

    public void zip()
            throws Exception
    {
        // File zipFile = new File(zipFileName);
        log.debug("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊----压缩开始----＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
        // 创建zip输出流
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));

        // 创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);

        File sourceFile = new File(sourceFileName);

        // 调用函数
        compress(out, bos, sourceFile, sourceFile.getName());
        bos.close();
        out.close();
        log.debug("＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊----压缩成功----＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊");
    }

    public void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile,
                         String base,List<String> specialPaths)
        throws Exception
    {
        // 如果路径为目录（文件夹）
        if (sourceFile.isDirectory())
        {

            // 取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();

            if (flist.length == 0)// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            }
            else// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for (int i = 0; i < flist.length; i++ )
                {
                    compress(out, bos, flist[i], base + "/" + flist[i].getName(),specialPaths);
                }
            }
        }
        else if(specialPaths.contains(sourceFile.getAbsolutePath()))// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            log.debug("正在压缩：[{}]",sourceFile.getAbsoluteFile());
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            System.out.println(base);
            // 将源文件写入到zip文件中
            while ((tag = bis.read()) != -1)
            {
                bos.write(tag);
            }
            bis.close();
            fos.close();
            log.debug("压缩完成：[{}]",sourceFile.getAbsoluteFile());
        }
    }

    public void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile,
                         String base)
            throws Exception
    {
        // 如果路径为目录（文件夹）
        if (sourceFile.isDirectory())
        {

            // 取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();

            if (flist.length == 0)// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            }
            else// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for (int i = 0; i < flist.length; i++ )
                {
                    compress(out, bos, flist[i], base + "/" + flist[i].getName());
                }
            }
        }
        else// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            log.debug("正在压缩：[{}]",sourceFile.getAbsoluteFile());
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            System.out.println(base);
            // 将源文件写入到zip文件中
            while ((tag = bis.read()) != -1)
            {
                bos.write(tag);
            }
            bis.close();
            fos.close();
            log.debug("压缩完成：[{}]",sourceFile.getAbsoluteFile());
        }
    }
}