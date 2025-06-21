
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "paid_leave_request")
@Data
public class PaidLeaveRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paid_leave_request")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_document", nullable = false)
    private DocumentEntity document;

    @ManyToOne
    @JoinColumn(name = "id_document_typ", nullable = false)
    private DocumentTypeEntity documentType;

    @Column(name = "paid_leave_days", nullable = false, precision = 3, scale = 1)
    private BigDecimal paidLeaveDays;

    @Column(name = "leave_reason", nullable = false, length = 255)
    private String leaveReason;

    @Column(name = "date_of_leave", nullable = false)
    private LocalDateTime dateOfLeave;

    @Column(name = "paid_leave_created_at", nullable = false)
    private LocalDateTime createdAt;
}
