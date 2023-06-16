package ltjv.dacs.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ltjv.dacs.entity.Category;
import ltjv.dacs.entity.Perfume;
import ltjv.dacs.service.CategoryService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryServices;
    @GetMapping
    public String showAllCategory(
            @NotNull Model model) {
        model.addAttribute("categories", categoryServices.getAllCategories());
        return "category/list";
    }

    @GetMapping("/add")
    public String addCategoryForm(@NotNull Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }
    @PostMapping("/add")
    public String addCategory(
            @Valid @ModelAttribute("category") Category category,
            @NotNull BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            return "category/add";
        }
        categoryServices.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@NotNull Model model, @PathVariable long id)
    {
        Category editCategory = categoryServices.getCategoryById(id);
        if(editCategory != null) {
            model.addAttribute("category", editCategory);
            return "category/edit";
        }else{
            return "not-found";
        }
    }
    @PostMapping("/edit")
    public String editProduct(@ModelAttribute("category") Category category) {
        categoryServices.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        categoryServices.deleteCategoryById(id);
        return "redirect:/categories";
    }
}
