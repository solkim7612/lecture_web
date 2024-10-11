// src/model/EnrollmentDAO.java
package lecture_list;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import DBConnection.DBConnection;
import Model.Classes;

public class EnrollmentDAO {

    // 학생이 이미 신청한 강의 목록 조회
    public Map<Integer, Integer> getEnrolledCourses(int studentId) {
        Map<Integer, Integer> enrolledCourses = new HashMap<>();
        String sql = "SELECT course_id, class_id FROM Enrollment WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int courseId = rs.getInt("course_id");
                    int classId = rs.getInt("class_id");
                    enrolledCourses.put(courseId, classId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return enrolledCourses;
    }

    // 학생이 강의를 신청하는 메소드
    // 반환 값:
    // 0: 성공
    // 1: 이미 신청한 강좌
    // 2: 마감된 강좌
    // -1: 기타 오류
    public int enrollCourse(int studentId, int courseId, int classId) {
        String checkEnrollmentSQL = "SELECT COUNT(*) AS cnt FROM Enrollment WHERE student_id = ? AND course_id = ?";
        String checkCapacitySQL = "SELECT capacity, enrolled FROM Class WHERE class_id = ?";
        String insertEnrollmentSQL = "INSERT INTO Enrollment (student_id, course_id, class_id) VALUES (?, ?, ?)";
        String updateEnrolledSQL = "UPDATE Class SET enrolled = enrolled + 1 WHERE class_id = ?";

        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtCheckCapacity = null;
        PreparedStatement pstmtInsert = null;
        PreparedStatement pstmtUpdateEnrolled = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 이미 신청한 강좌인지 확인
            pstmtCheck = conn.prepareStatement(checkEnrollmentSQL);
            pstmtCheck.setInt(1, studentId);
            pstmtCheck.setInt(2, courseId);
            rs = pstmtCheck.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("cnt");
                if (count > 0) {
                    conn.rollback();
                    return 1; // 이미 신청한 강좌
                }
            }

            // 2. 강좌 정원이 마감되지 않았는지 확인
            pstmtCheckCapacity = conn.prepareStatement(checkCapacitySQL);
            pstmtCheckCapacity.setInt(1, classId);
            rs = pstmtCheckCapacity.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                int enrolled = rs.getInt("enrolled");
                if (enrolled >= capacity) {
                    conn.rollback();
                    return 2; // 마감된 강좌
                }
            } else {
                conn.rollback();
                return -1; // 강좌 정보 없음
            }

            // 3. Enrollment 테이블에 삽입
            pstmtInsert = conn.prepareStatement(insertEnrollmentSQL);
            pstmtInsert.setInt(1, studentId);
            pstmtInsert.setInt(2, courseId);
            pstmtInsert.setInt(3, classId);
            int insertedRows = pstmtInsert.executeUpdate();
            if (insertedRows == 0) {
                conn.rollback();
                return -1; // 삽입 실패
            }

            // 4. Class 테이블의 enrolled 값 증가
            pstmtUpdateEnrolled = conn.prepareStatement(updateEnrolledSQL);
            pstmtUpdateEnrolled.setInt(1, classId);
            int updatedRows = pstmtUpdateEnrolled.executeUpdate();
            if (updatedRows == 0) {
                conn.rollback();
                return -1; // 업데이트 실패
            }

            conn.commit();
            return 0; // 성공

        } catch (SQLException e) {
            e.printStackTrace(); 
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(); 
            }
            return -1; // 기타 오류
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmtCheck != null)
                    pstmtCheck.close();
                if (pstmtCheckCapacity != null)
                    pstmtCheckCapacity.close();
                if (pstmtInsert != null)
                    pstmtInsert.close();
                if (pstmtUpdateEnrolled != null)
                    pstmtUpdateEnrolled.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
    }

    // 학생이 강의를 취소하는 메소드
    // 반환 값:
    // 0: 취소 성공
    // 1: 신청하지 않은 강좌
    // -1: 취소 실패 (기타 오류)
    public int unenrollCourse(int studentId, int courseId, int classId) {
        String checkEnrollmentSQL = "SELECT COUNT(*) AS cnt FROM Enrollment WHERE student_id = ? AND course_id = ?";
        String deleteEnrollmentSQL = "DELETE FROM Enrollment WHERE student_id = ? AND course_id = ?";
        String updateEnrolledSQL = "UPDATE Class SET enrolled = enrolled - 1 WHERE class_id = ? AND enrolled > 0";

        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtDelete = null;
        PreparedStatement pstmtUpdate = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 신청한 강좌인지 확인
            pstmtCheck = conn.prepareStatement(checkEnrollmentSQL);
            pstmtCheck.setInt(1, studentId);
            pstmtCheck.setInt(2, courseId);
            rs = pstmtCheck.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("cnt");
                if (count == 0) {
                    conn.rollback();
                    return 1; // 신청하지 않은 강좌
                }
            }

            // 2. Enrollment 테이블에서 삭제
            pstmtDelete = conn.prepareStatement(deleteEnrollmentSQL);
            pstmtDelete.setInt(1, studentId);
            pstmtDelete.setInt(2, courseId);
            int deletedRows = pstmtDelete.executeUpdate();
            if (deletedRows == 0) {
                conn.rollback();
                return -1; // 삭제 실패
            }

            // 3. Class 테이블의 enrolled 값 감소
            pstmtUpdate = conn.prepareStatement(updateEnrolledSQL);
            pstmtUpdate.setInt(1, classId);
            int updatedRows = pstmtUpdate.executeUpdate();
            if (updatedRows == 0) {
                conn.rollback();
                return -1; // 감소 실패
            }

            conn.commit();
            return 0; // 취소 성공

        } catch (SQLException e) {
            e.printStackTrace(); 
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(); 
            }
            return -1; // 기타 오류
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmtCheck != null)
                    pstmtCheck.close();
                if (pstmtDelete != null)
                    pstmtDelete.close();
                if (pstmtUpdate != null)
                    pstmtUpdate.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
    }

    // 학생의 현재 수강 학점 가져오기
    public int getCurrentCredits(int studentId) {
        int totalCredits = 0;
        String sql = "SELECT SUM(c.credit) AS total_credits FROM Enrollment e JOIN Class cl ON e.class_id = cl.class_id JOIN Course c ON cl.course_id = c.course_id WHERE e.student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalCredits = rs.getInt("total_credits");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCredits;
    }
}
