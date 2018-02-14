package com.vero.dm.service.impl;


import static com.vero.dm.repository.specifications.StudentSpecifications.findStudentsByMultipleParams;
import static com.vero.dm.util.PathUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.importer.core.ExcelExporter;
import com.vero.dm.importer.core.ExcelImporter;
import com.vero.dm.importer.core.ExcelModuleManager;
import com.vero.dm.model.FavoriteStatus;
import com.vero.dm.model.Student;
import com.vero.dm.model.StudentStatus;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.service.StudentService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Service
@Slf4j
public class StudentServiceImpl extends UserServiceImpl implements StudentService
{
    @Autowired
    private ExcelImporter<Student> studentExcelImporter;

    @Autowired
    private ExcelModuleManager excelModuleManager;

    @Autowired
    private ExcelExporter<Student> studentExcelExporter;

    @Override
    public List<StudentDto> getStudentList()
    {
        // List<Student> students = studentDao.getStudentList();
        // List<StudentDto> list = new LinkedList<StudentDto>();
        // for (Student student : students)
        // {
        // list.add(StudentDto.build(student));
        // }
        // return list;
        return null;
    }

    @Override
    public List<Student> importStudents(MultipartFile file)
    {
        String relativePath = concat("students", "import");
        String dir = getAbsolutePath(relativePath);
        String absolutePath = concat(dir, file.getOriginalFilename());
        handleFileTransfer(file, absolutePath);
        List<Student> students = studentExcelImporter.importFromExcel(new File(absolutePath));
        studentJpaRepository.save(students);
        log.debug("Import student data form excel file [{}].", file.getName());
        return students;
    }

    @Override
    public byte[] handleStudentExcelModuleDownload()
    {
        String filePath = excelModuleManager.getModulePath(Student.class);
        File file = new File(filePath);
        if (file.exists() && file.isFile())
        {
            try
            {
                return FileUtils.readFileToByteArray(file);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    // @Override
    // public void handleStudentExcelModuleDownload(HttpServletResponse response)
    // {
    // String filePath = excelModuleManager.getModulePath(Student.class);
    // try
    // {
    // File file = new File(filePath);
    // if (file.exists())
    // {
    // String filename = file.getName();
    // InputStream fis = new BufferedInputStream(new FileInputStream(file));
    // response.reset();
    // response.setContentType("application/octet-stream");
    // response.addHeader("Content-Disposition",
    // "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
    // response.addHeader("Content-Length", "" + file.length());
    // OutputStream out = new BufferedOutputStream(response.getOutputStream());
    // byte[] buffer = new byte[1024 * 1024];
    // int i = -1;
    // while ((i = fis.read(buffer)) != -1)
    // {
    // out.write(buffer, 0, i);
    // out.flush();
    // }
    // fis.close();
    // out.flush();
    // out.close();
    // // try
    // // {
    // // response.wait();
    // // }
    // // catch (InterruptedException e)
    // // {
    // // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // // }
    // }
    // }
    // catch (IOException e)
    // {
    // e.printStackTrace();
    // }
    // }

    @Override
    public List<String> getStudentIds()
    {
        // return studentDao.getStudentIds();
        return null;
    }

    @Override
    public List<Student> findAllStudents()
    {
        return studentJpaRepository.findAll();
    }

    @Override
    public Page<Student> getStudentList(Pageable pageable, String className, String profession,
                                        String grade, String studentIdPrefix,String studentName)
    {
        return studentJpaRepository.findAll(
            findStudentsByMultipleParams(className, profession, grade, studentIdPrefix,studentName), pageable);
    }

    @Override
    public StudentDto deleteByStudentId(String studentId)
    {
        // Student student = studentDao.getStudentById(studentId);
        // studentDao.deleteStudentById(studentId);
        // return StudentDto.build(student);
        return null;
    }

    public StudentDto save(Student student)
    {
        FavoriteStatus favoriteStatus = this.getFavoriteStatusPersisted(
            student.getFavorite().getFavoriteId());
        StudentStatus studentStatus = this.getStudentStatusPersisted(
            student.getStatus().getStatusId());
        student.setFavorite(favoriteStatus);
        student.setStatus(studentStatus);
//        this.registerUser(student);
        return new StudentDto(student);
    }

    @Override
    public StudentDto update(StudentDto studentDto)
    {
        // Student student = studentDao.getStudentById(studentDto.getStudentId());
        // BeanUtils.copyProperties(studentDto, student, "status", "favorite");
        // studentDao.update(student);
        // return new StudentDto(student);
        return studentDto;
    }

    @Override
    public StudentDto getStudentById(String studentId)
    {
        // return StudentDto.build(studentDao.getStudentById(studentId));
        return null;
    }

    @Override
    public int markStudents(List<String> studentIds)
    {
        // return studentDao.markStudents(studentIds);
        return 0;
    }

    @Override
    public int unMarkStudents(List<String> studentIds)
    {
        // return studentDao.unMarkStudents(studentIds);
        return 0;
    }

    @Override
    public List<Student> deleteBatchByStudentIds(List<String> studentIds)
    {
        List<Student> students = studentJpaRepository.findByStudentIds(studentIds);
        studentJpaRepository.deleteBatchStudentsById(studentIds);
        return students;
    }

    @Override
    public FavoriteStatus getFavoriteStatusPersisted(Integer statusId)
    {
        // return studentDao.getFavoriteStatusPersisted(statusId);
        return null;
    }

    @Override
    public StudentStatus getStudentStatusPersisted(Integer statusId)
    {
        // return studentDao.getStudentStatus(statusId);
        return null;
    }

    public List<Student> fetchStudentWithoutTasks()
    {
        return null;
    }
}
