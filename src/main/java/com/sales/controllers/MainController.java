package com.sales.controllers;

import com.sales.models.Book;
import com.sales.models.Customer;
import com.sales.models.Loan;
import com.sales.services.BookService;
import com.sales.services.CustomerService;
import com.sales.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@SessionAttributes({"book", "customer", "loan"})
public class MainController {
    // Autowire for Services
    @Autowired
    BookService bookService;

    @Autowired
    CustomerService customerService;

    @Autowired
    LoanService loanService;


    // == Book Methods ==============================================================
    /*
    Method to create a book object
    Request mapping /addBook.
    Return addBook page
     */
    @RequestMapping(value = "/addBook", method = RequestMethod.GET)
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        System.out.println("Book Title: " + book.getTitle());
        return "addBook";
    }

    /*
    Method to add a new book object to the database
    Request mapping /addBook.
    Redirect /showBooks
     */
    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public String addBookPost(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        // If input has an error, return to addBook page
        if (result.hasErrors()) {
            return "addBook";
        } else {// End if
            System.out.println("Book title: " + book.getTitle());
            bookService.save(book);
            return "redirect:showBooks";
        }
    }

    /*
    Method to get all book entry's from the database and store them in a list of book objects
    Request mapping /showBooks.
    return /showBooks
     */
    @RequestMapping(value = "/showBooks")
    public String getBooks(Model model) {
        // List of book objects
        List<Book> books = (List<Book>) bookService.getBooks();
        // Create objects from model book and all to the array list
        model.addAttribute("books", books);
        return "showBooks";
    }// End getBooks method

    // == End Book Methods ===========================================================
    // == Customer Methods ===========================================================

    /*
   Method to create a customer object
   Request mapping /addBook.
   Return addBook page
    */
    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String addCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        System.out.println("Customer name: " + customer.getcName());
        return "addCustomer";
    }

    /*
  Method to add a new customer object to the database
  Request mapping /addCustomer.
  Redirect /showCustomers
   */
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public String addCustomerPost(@Valid @ModelAttribute("customer") Customer customer, BindingResult result) {
        // If input has an error, return to addCustomer page
        if (result.hasErrors()) {
            return "addCustomer";
        } else {// End if
            System.out.println("Customer name: " + customer.getcName());
            customerService.save(customer);
            return "redirect:showCustomers";
        }
    }

    /*
   Method to get all customer entry's from the database and store them in a list of customer objects
   Request mapping /showCustomers.
   return /showCustomers
    */
    @RequestMapping(value = "/showCustomers")
    public String getCustomers(Model m) {
        // List of book objects
        List<Customer> customers = (List<Customer>) customerService.getCustomers();
        // Create objects from model book and all to the array list
        m.addAttribute("customers", customers);
        //Create objects from model loan and add to the array list
        return "showCustomers";
    }// End getBooks method

    // == End Customer Methods ===========================================================
    // == Loan Methods ===================================================================

    /*
  Method to create a new loan object
  Request mapping /newLoan.
  Return newLoan page
   */
    @RequestMapping(value = "/newLoan", method = RequestMethod.GET)
    public String addLoan(Model model) {
        Loan loan = new Loan();
        model.addAttribute("loan", loan);
        return "newLoan";
    }

    /*
  Method to add a new loan object to the database
  Request mapping /addCustomer.
  Redirect /showCustomers
   */
    @RequestMapping(value = "/newLoan", method = RequestMethod.POST)
    public String addLoanPost(@Valid @ModelAttribute("loan") Loan loan, BindingResult result) {
        // If input has an error, return to newLoan page
        if (result.hasErrors()) {
            return "newLoan";
        } else {// End if
            loanService.save(loan);
            return "redirect:showLoans";
        }// End if else
    }// End method


    /*
  Method to get all loan entry's from the database and store them in a list of loan objects
  Request mapping /showCustomers.
  return /showCustomers
   */
    @RequestMapping(value = "/showLoans")
    public String getLoans(Model model) {
        // List of book objects
        List<Loan> loans = (List<Loan>) loanService.getLoans();
        // Create objects from model book and all to the array list
        model.addAttribute("loans", loans);

        return "showLoans";
    }// End getBooks method

    @RequestMapping(value = "/deleteLoan", method = RequestMethod.GET)
    public String getDeleteLoan(Model m) {
        Loan loan = new Loan();
        m.addAttribute("loan", loan);
        return "deleteLoan";
    }

    @RequestMapping(value = "/deleteLoan", method = RequestMethod.POST)
    public String deleteLoan(@Valid @ModelAttribute("loan") Loan loan, BindingResult result) {
        // If input has an error, return to newLoan page
        if (result.hasErrors()) {
            return "newLoan"; //return to a new page if error
        } else {// End if
            loanService.deleteLoanByLid(loan.getLid());
            return "redirect:showLoans";
        }// End if else
    }// End method
}// End main controller class
