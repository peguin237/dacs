package ltjv.dacs.controller;

import lombok.RequiredArgsConstructor;
import ltjv.dacs.service.CartService;
import ltjv.dacs.service.CategoryService;
import ltjv.dacs.service.PerfumeService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ltjv.dacs.service.CartService;
import ltjv.dacs.service.CategoryService;
import ltjv.dacs.service.PerfumeService;
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final PerfumeService perfumeService;

    private final CategoryService categoryService;
    @GetMapping
    public String home(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("perfumes", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("totalPages", perfumeService.getAllPerfumes(pageNo, pageSize, sortBy).size() / pageSize);
        return "home/index";
    }
}
