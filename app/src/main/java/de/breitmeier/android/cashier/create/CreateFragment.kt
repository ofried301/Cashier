package de.breitmeier.android.cashier.create

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import de.breitmeier.android.cashier.R
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.database.productdb.ProductDatabase
import de.breitmeier.android.cashier.databinding.CreateFragmentBinding
import kotlinx.android.synthetic.main.create_fragment.*

const val IMAGE_REQUEST_CODE = 1234

class CreateFragment: Fragment() {

    private val TAG = "CreateFragment.kt"

    private lateinit var selectImgProduct: Product
    private lateinit var createViewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CreateFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.create_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ProductDatabase.getInstance(application).productDatabaseDao

        val viewModelFactory = CreateViewModelFactory(dataSource)

        createViewModel = ViewModelProvider(this, viewModelFactory)
            .get(CreateViewModel::class.java)

        binding.createViewModel = createViewModel
        binding.lifecycleOwner = this

        val adapter = CreateListAdapter(
            CreateListAdapter.DeleteClickListener {
                createViewModel.onDeleteClicked(it)
            },
            CreateListAdapter.SelectPictureClickListener {
                selectImageInAlbum(it)
                selectImgProduct = it
            }
        )

        binding.btnAdd.setOnClickListener {
            if (hasNoMistakes()) {
                createViewModel.onAddClicked(
                    et_name.text.toString(),
                    et_price.text.toString(),
                    et_deposit.text.toString(),
                    cb_deposit.isChecked)
            }
        }

        createViewModel.products.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        val manager = LinearLayoutManager(context)
        binding.rvProducts.layoutManager = manager

        binding.rvProducts.adapter = adapter

        return binding.root
    }

    private fun hasNoMistakes(): Boolean {
        var mistakeCount = 0
        if (!isNameValid(et_name.text)) {
            text_input_name.error = "Please enter valid name."
            mistakeCount++
        } else text_input_name.error = null

        if (!isPriceValid(et_price.text)) {
            text_input_price.error = "Please enter valid price."
            mistakeCount++
        } else text_input_price.error = null

        if (!cb_deposit.isChecked && !isDepositValid(et_deposit.text)) {
            text_input_deposit.error = "Please enter valid deposit."
            mistakeCount++
        } else text_input_deposit.error = null

        return mistakeCount == 0
    }

    private fun isPriceValid(text: Editable?): Boolean {
        return text.toString() != "" && text != null
    }

    private fun isDepositValid(text: Editable?): Boolean {
        return text != null && text.toString() != ""
    }

    private fun isNameValid(text: Editable?): Boolean {
        return text != null && text.toString() != ""
    }

    private fun selectImageInAlbum(product: Product) {
        val intent = Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
        selectImgProduct = product
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && data != null) {

            selectImgProduct.imgUri = data.dataString.toString()
            createViewModel.updateProductImg(selectImgProduct)
        }
    }
}




















