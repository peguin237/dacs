package ltjv.dacs.controller;

import jakarta.servlet.http.HttpSession;
import ltjv.dacs.daos.Item;
import ltjv.dacs.entity.Perfume;
import ltjv.dacs.service.CartService;
import ltjv.dacs.service.CategoryService;
import ltjv.dacs.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;

@Controller
@RequestMapping("/perfumes")
@RequiredArgsConstructor
public class PerfumeController {
    private final PerfumeService perfumeService;

    private final CategoryService categoryService;
    private final CartService cartService;
    @GetMapping
    public String showAllBooks(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "perfume/list";
    }
    @GetMapping("/add")
    public String addPerfumeForm(@NotNull Model model) {
        model.addAttribute("perfume", new Perfume());
        model.addAttribute("categories",
                categoryService.getAllCategories());
        return "perfume/add";
    }
    @PostMapping("/add")
    public String addPerfume(
            @Valid @ModelAttribute("perfume") Perfume perfume,
            @NotNull BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "perfume/add";
        }
        perfumeService.addPerfume(perfume);
        return "redirect:/perfumes";
    }

    @GetMapping("/edit/{id}")
    public String editPerfumeForm(@NotNull Model model, @PathVariable long id)
    {
        var perfume = perfumeService.getPerfumeById(id);
        model.addAttribute("perfume", perfume.orElseThrow(() -> new IllegalArgumentException("Perfume not found")));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "perfume/edit"; }
    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("perfume") Perfume perfume,
                           @NotNull BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories",
                    categoryService.getAllCategories());
            return "perfume/edit";
        }
        perfumeService.updatePerfume(perfume);
        return "redirect:/perfumes"; }

    @GetMapping("/delete/{id}")
    public String deletePerfume(@PathVariable long id) {
        perfumeService.getPerfumeById(id)
                .ifPresentOrElse(
                        book -> perfumeService.deletePerfumeById(id),
                        () -> { throw new IllegalArgumentException("Book not found"); });
                            return "redirect:/perfuems"; }
    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam long id,
                            @RequestParam String name,
                            @RequestParam double price,
                            @RequestParam(defaultValue = "1") int quantity)
    {
        var cart = cartService.getCart(session);
        cart.addItems(new Item(id, name, price, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/perfumes"; }

    @GetMapping("/search")
    public String searchBook(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.searchPerfume(keyword));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages",
                perfumeService
                        .getAllPerfumes(pageNo, pageSize, sortBy)
                        .size() / pageSize);
        model.addAttribute("categories",
                categoryService.getAllCategories());
        return "perfume/list"; }
}
