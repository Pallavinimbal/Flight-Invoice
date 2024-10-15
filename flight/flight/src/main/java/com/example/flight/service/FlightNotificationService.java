package com.example.flight.service;

import com.example.flight.dto.FlightNotificationDto;
import com.lowagie.text.DocumentException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

@Service
public class FlightNotificationService {

    @Autowired
    private JavaMailSender mailSender; // Mail sender bean

    @Autowired
    private freemarker.template.Configuration freemarkerConfig; // Freemarker configuration for PDF template

    // Generates the flight notification PDF
    public byte[] createFlightNotificationPdf(FlightNotificationDto notificationDto) throws IOException, TemplateException {
        return generatePdf(notificationDto);
    }

    public void sendFlightNotificationEmail(FlightNotificationDto notificationDto, String toEmail) throws IOException, TemplateException, MessagingException {

        byte[] pdfBytes = createFlightNotificationPdf(notificationDto);

        // email with the PDF attachment
        sendEmailWithAttachment(toEmail, "Your Flight Ticket", "Please find your flight ticket attached.", pdfBytes, "FlightTicket.pdf");
    }


    private byte[] generatePdf(FlightNotificationDto notificationDto) throws IOException, TemplateException {
        // Load the PDF template
        Template pdfTemplate = freemarkerConfig.getTemplate("pdf-template.ftl");

        // Prepare the model with null checks to avoid NPE
        Map<String, Object> model = Map.ofEntries(
                new SimpleEntry<>("passengerName", notificationDto.getPassengerName() != null ? notificationDto.getPassengerName() : "N/A"),
                new SimpleEntry<>("bookingReference", notificationDto.getBookingReference() != null ? notificationDto.getBookingReference() : "N/A"),
                new SimpleEntry<>("ticketNumber", notificationDto.getTicketNumber() != null ? notificationDto.getTicketNumber() : "N/A"),
                new SimpleEntry<>("issuingOffice", notificationDto.getIssuingOffice() != null ? notificationDto.getIssuingOffice() : "N/A"),
                new SimpleEntry<>("flightDate", notificationDto.getFlightDate() != null ? notificationDto.getFlightDate() : "N/A"),
                new SimpleEntry<>("departureCity", notificationDto.getDepartureCity() != null ? notificationDto.getDepartureCity() : "N/A"),
                new SimpleEntry<>("arrivalCity", notificationDto.getArrivalCity() != null ? notificationDto.getArrivalCity() : "N/A"),
                new SimpleEntry<>("flightClass", notificationDto.getFlightClass() != null ? notificationDto.getFlightClass() : "N/A"),
                new SimpleEntry<>("baggage", notificationDto.getBaggage() != null ? notificationDto.getBaggage() : "N/A"),
                new SimpleEntry<>("fareBasis", notificationDto.getFareBasis() != null ? notificationDto.getFareBasis() : "N/A"),
                new SimpleEntry<>("operatedBy", notificationDto.getOperatedBy() != null ? notificationDto.getOperatedBy() : "N/A"),
                new SimpleEntry<>("marketedBy", notificationDto.getMarketedBy() != null ? notificationDto.getMarketedBy() : "N/A"),
                //new SimpleEntry<>("issueDate", notificationDto.getIssueDate() != null ? notificationDto.getIssueDate() : "N/A")  // issueDate mapping
                new SimpleEntry<>("issueDate", notificationDto.getIssueDate() != null ? notificationDto.getIssueDate() : LocalDateTime.now())

        );

            // HTML content
            StringWriter writer = new StringWriter();
            pdfTemplate.process(model, writer);
            String htmlContent = writer.toString();

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);
                renderer.layout();
                renderer.createPDF(baos);
                return baos.toByteArray();
            } catch (DocumentException de) {
                throw new IOException("Error generating PDF document: " + de.getMessage(), de);
            }
        }

        // Private method to send an email with the PDF attachment
        private void sendEmailWithAttachment (String toEmail, String subject, String body,byte[] pdfBytes, String
        attachmentName) throws MessagingException {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            Resource pdfResource = new ByteArrayResource(pdfBytes);
            helper.addAttachment(attachmentName, pdfResource);

            mailSender.send(message);
        }
    }



