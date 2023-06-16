package ltjv.dacs.controller;

import jakarta.servlet.http.HttpSession;
import ltjv.dacs.daos.Item;
import ltjv.dacs.entity.Perfume;
import ltjv.dacs.service.CartService;
import ltjv.dacs.service.CategoryService;
import ltjv.dacs.service.PerfumeService;
import ltjv.dacs.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/perfumes")
@RequiredArgsConstructor
public class PerfumeController {
    private final PerfumeService perfumeService;

    private final CategoryService categoryService;
    private final CartService cartService;
    @GetMapping
    public String showAllPerfumes(
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
    @GetMapping("/gucci")
    public String showGucci(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "perfume/gucci";
    }
    @GetMapping("/lancome")
    public String showLancome(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "perfume/lancome";
    }
    @GetMapping("/burberry")
    public String showBurberry(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "perfume/burberry";
    }

    @GetMapping("/dior")
    public String showDior(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "perfume/dior";
    }

    @GetMapping("/chanel")
    public String showChanel(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "perfume/chanel";
    }

    @GetMapping("/ad")
    public String showallBook(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "perfume/ad";
    }
    @GetMapping("/add")
    public String addPerfumeForm(@NotNull Model model) {
        model.addAttribute("perfume", new Perfume());
        model.addAttribute("categories", categoryService.getAllCategories());
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

    @GetMapping("/detail/{id}")
    public String detailPerfumeForm(@NotNull Model model, @PathVariable long id)
    {
        Perfume detailPerfume = perfumeService.getPerfumeById(id);
        if(detailPerfume != null) {
            model.addAttribute("perfume", detailPerfume);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "perfume/detail";
        }else{
            return "not-found";
        }
    }
    @PostMapping("/detail")
    public String detailProduct(@ModelAttribute("perfume") Perfume perfume) {
        perfumeService.addPerfume(perfume);
        return "redirect:/perfumes";
    }

    @GetMapping("/edit/{id}")
    public String editPerfumeForm(@NotNull Model model, @PathVariable long id)
    {
        Perfume editPerfume = perfumeService.getPerfumeById(id);
        if(editPerfume != null) {
            model.addAttribute("perfume", editPerfume);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "perfume/edit";
        }else{
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String editProduct(@ModelAttribute("perfume") Perfume perfume) {
        perfumeService.addPerfume(perfume);
        return "redirect:/perfumes";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        perfumeService.deletePerfumeById(id);
        return "redirect:/perfumes";
    }
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
