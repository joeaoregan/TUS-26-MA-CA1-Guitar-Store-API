/*
 * Helper Class.
 * Ensures the created_by and updated_by fields
 * show a specific value instead of being empty.
 */

package edu.tus.guitarstore.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    /**
     * getCurrentAuditor method returns the current auditor's name,
     * which is hardcoded as "Joe" for demonstration purposes.
     * In a real application, this would typically retrieve
     * the username of the currently authenticated user.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Joe");
    }
}
