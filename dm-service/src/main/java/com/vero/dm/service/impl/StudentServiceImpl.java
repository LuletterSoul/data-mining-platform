package com.vero.dm.service.impl;


import static com.vero.dm.repository.specifications.StudentSpecifications.findLeisureStudents;
import static com.vero.dm.repository.specifications.StudentSpecifications.findStudentsWithParams;
import static com.vero.dm.util.PathUtils.concat;
import static com.vero.dm.util.PathUtils.getAbsolutePath;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.vero.dm.exception.group.StudentNotFoundException;
import com.vero.dm.model.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.exception.business.StudentIdDuplicatedException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.importer.core.ExcelExporter;
import com.vero.dm.importer.core.ExcelImporter;
import com.vero.dm.importer.core.ExcelModuleManager;
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

    public final static String MAC_DEFAULT_ALGORITHM = "HmacSHA256";

    public final static String SERCRET_KEY_DEFAULT_ALGORITHM = "HMACSHA256";

    @Override
    public List<Student> importStudents(MultipartFile file)
    {
        String relativePath = concat("students", "import");
        String dir = getAbsolutePath(relativePath);
        String absolutePath = concat(dir, file.getOriginalFilename());
        try
        {
            file.transferTo(new File(absolutePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        List<Student> students = studentExcelImporter.importFromExcel(new File(absolutePath));
        checkStudentIdDuplication(students);
        for (Student student : students)
        {
            // 初始化用户名密码皆为学号，密码为明文;
            student.setPassword(student.getStudentId());
            student.setUsername(student.getStudentId());
        }
        studentJpaRepository.save(students);
        log.debug("Import Student Data Form Excel [{}].", file.getOriginalFilename());
        return students;
    }

    @Override
    public StudentDto findByUsername(String username) {
        Student student = studentJpaRepository.findByUsername(username);
        if (student == null) {
            student = new Student();
        }
        return StudentDto.build(student);
    }

    @Override
    public StudentDto findByUserId(String userId) {
        return StudentDto.build(studentJpaRepository.findByStudentId(userId));
    }

    private void checkStudentIdDuplication(List<Student> students)
    {
        List<String> existedStudentIds = getStudentIds();
        StringBuilder message = new StringBuilder("[");
        students.forEach(s -> {
            if (existedStudentIds.contains(s.getStudentId()))
            {
                message.append(s.getStudentId()).append(",");
            }
        });
        if (!message.toString().equals("["))
        {
            message.replace(message.length() - 1, message.length(), "]");
            message.append(" Student Id is Duplicated.");
            log.error(message.toString());
            throw new StudentIdDuplicatedException(message.toString(),
                ExceptionCode.StudentIdDuplicated);
        }
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

    @Override
    public List<String> getStudentIds()
    {
        return studentJpaRepository.findAllStudentIds();
    }

    @Override
    public List<Student> findAllStudents()
    {
        return studentJpaRepository.findAll();
    }

    @Override
    public Student findStudentById(String id)
    {
        return studentJpaRepository.findOne(id);
    }

    @Override
    public Student findByStudentId(String studentId)
    {
        return studentJpaRepository.findByStudentId(studentId);
    }

    @Override
    public List<Student> findByStudentIds(List<String> ids)
    {
        return studentJpaRepository.findByStudentIds(ids);
    }

    @Override
    public Page<Student> getStudentList(boolean fetch, Pageable pageable, String className,
                                        String profession, String grade, String studentIdPrefix,
                                        String studentName, Date beginDate, Date endDate)
    {
        if (fetch)
        {
            return new PageImpl<>(studentJpaRepository.findAll(findStudentsWithParams(className,
                profession, grade, studentIdPrefix, studentName)));
        }
        else if (Objects.isNull(endDate) && Objects.isNull(beginDate))
        {
            return studentJpaRepository.findAll(
                findStudentsWithParams(className, profession, grade, studentIdPrefix, studentName),
                pageable);
        }
        else
        {
            return studentJpaRepository.findAll(findLeisureStudents(className, profession, grade,
                studentIdPrefix, studentName, beginDate, endDate), pageable);
        }
    }

    @Override
    public List<Student> getAllLeisureStudents(Pageable pageable, String className,
                                               String profession, String grade,
                                               String studentIdPrefix, String studentName,
                                               Date beginDate, Date endDate)
    {
        return studentJpaRepository.findAll(findLeisureStudents(className, profession, grade,
            studentIdPrefix, studentName, beginDate, endDate));
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
        this.studentJpaRepository.save(student);
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
    public StudentDto getStudent(String userId, String username) {
        StudentDto stuByUserId = findByUserId(userId);
        StudentDto stuByUsername = findByUsername(username);
        if (Objects.isNull(stuByUserId) && Objects.isNull(stuByUsername)) {
            throw new StudentNotFoundException("此用户不具有学生身份", ExceptionCode.StudentNotFound);
        }

        if (!Objects.isNull(stuByUserId)) {
            return stuByUserId;
        }
        return stuByUsername;
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
        List<String> studentUserIds = students.stream().map(Student::getUserId).collect(Collectors.toList());
        students.forEach(s -> {
            s.setMiningGroups(null);
            Set<DataMiningGroup> groups = s.getRuleMiningGroups();
            groups.forEach(g -> g.setGroupLeader(null));
            groupJpaRepository.save(groups);
        });
        //删除上交的挖掘记录
        miningResultRepository.deleteMiningResultByMembers(studentUserIds);
        studentJpaRepository.save(students);
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

    @Override
    public Map<String, List<?>> getStudentPropertiesOptions()
    {
        Map<String, List<?>> options = new HashMap<>();
        options.put("classNameOptions", studentJpaRepository.findClassNameOptions());
        options.put("professionOptions", studentJpaRepository.findProfessionOptions());
        options.put("gradeOptions", studentJpaRepository.findGradeOptions());
        return options;
    }

    public List<Student> fetchStudentWithoutTasks()
    {
        return null;
    }
}
