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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class FlightNotificationService {

    @Autowired
    private JavaMailSender mailSender; // Mail sender bean

    @Autowired
    private freemarker.template.Configuration freemarkerConfig; // Freemarker configuration for PDF template

    // Create a logger instance
    private static final Logger log = LoggerFactory.getLogger(FlightNotificationService.class);

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
          // Log the NVB Date
          log.info("NVB Date: {}", notificationDto.getNvbDate());
          // Load the PDF template
        Template pdfTemplate = freemarkerConfig.getTemplate("pdf-template.ftl");

          Map<String, Object> model = new HashMap<>();
          model.put("passengerName", notificationDto.getPassengerName() != null ? notificationDto.getPassengerName() : "N/A");
          model.put("bookingReference", notificationDto.getBookingReference() != null ? notificationDto.getBookingReference() : "N/A");
          model.put("ticketNumber", notificationDto.getTicketNumber() != null ? notificationDto.getTicketNumber() : "N/A");
          model.put("issuingOffice", notificationDto.getIssuingOffice() != null ? notificationDto.getIssuingOffice() : "N/A");
          model.put("issueDate", notificationDto.getIssueDate() != null ? notificationDto.getIssueDate() : LocalDateTime.now());
          model.put("flightDate", notificationDto.getFlightDate() != null ? notificationDto.getFlightDate() : "N/A");
          model.put("departureCity", notificationDto.getDepartureCity() != null ? notificationDto.getDepartureCity() : "N/A");
          model.put("arrivalCity", notificationDto.getArrivalCity() != null ? notificationDto.getArrivalCity() : "N/A");
          model.put("flightClass", notificationDto.getFlightClass() != null ? notificationDto.getFlightClass() : "N/A");
          model.put("baggage", notificationDto.getBaggage() != null ? notificationDto.getBaggage() : "N/A");
          model.put("fareBasis", notificationDto.getFareBasis() != null ? notificationDto.getFareBasis() : "N/A");
          model.put("operatedBy", notificationDto.getOperatedBy() != null ? notificationDto.getOperatedBy() : "N/A");
          model.put("marketedBy", notificationDto.getMarketedBy() != null ? notificationDto.getMarketedBy() : "N/A");
          model.put("departureAirport", notificationDto.getDepartureAirport() != null ? notificationDto.getDepartureAirport() : "N/A");
          model.put("arrivalAirport", notificationDto.getArrivalAirport() != null ? notificationDto.getArrivalAirport() : "N/A");
          model.put("terminal", notificationDto.getTerminal() != null ? notificationDto.getTerminal() : "N/A");
          model.put("nvbDate", notificationDto.getNvbDate() != null ? notificationDto.getNvbDate() : "N/A");
          model.put("flightDuration", notificationDto.getFlightDuration() != null ? notificationDto.getFlightDuration() : "N/A");
          model.put("bookingStatus1", notificationDto.getBookingStatus1() != null ? notificationDto.getBookingStatus1() : "N/A");
          model.put("nvaDate", notificationDto.getNvaDate() != null ? notificationDto.getNvaDate() : "N/A");


        // Generate the PDF using the populated model
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
private void sendEmailWithAttachment(String toEmail, String subject, String body, byte[] pdfBytes, String attachmentName) throws MessagingException {
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





