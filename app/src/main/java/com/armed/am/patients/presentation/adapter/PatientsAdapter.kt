package com.armed.am.patients.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.armed.am.databinding.ItemPatientInfoBinding
import com.armed.am.patients.network.model.PatientUIModel
import com.armed.am.utils.displayRoundedImage
import com.armed.am.utils.formatDate
import com.armed.am.utils.onClick

/**
 * Created by Levon Arzumanyan on 09/18/21.
 * Project Name: ARMED
 * NOORLOGIC
 */

class PatientsAdapter(private val onItemClicked: (PatientUIModel?) -> Unit) :
    PagingDataAdapter<PatientUIModel, PatientsAdapter.PatientsViewHolder>(DIFF_UTIL) {


    class PatientsViewHolder(private val binding: ItemPatientInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(data: PatientUIModel?) {
            binding.apply {
                with(data) {
                    patientImage.displayRoundedImage(this?.imageUrl)
                    typeBodyTv.text = this?.caseType
                    nameSurnameTv.text = "${this?.name} ${this?.sureName}"
                    birthdayDateBody.text = this?.dateOfBirth?.formatDate("yyyy-MM-dd", "dd.MM.yyyy")
                    visitDate.text = this?.visitStart?.formatDate("yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy")
                    visitTime.text =
                        "${this?.visitStart?.formatDate("yyyy-MM-dd HH:mm:ss", "HH:mm")} -" +
                                " ${this?.visitEnd?.formatDate("yyyy-MM-dd HH:mm:ss", "HH:mm")}"
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PatientsViewHolder, position: Int) {
        holder.apply {
            with(getItem(position)) {
                bindData(this)
                itemView.onClick { onItemClicked.invoke(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientsViewHolder {
        val binding = ItemPatientInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PatientsViewHolder(binding)
    }


    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<PatientUIModel>() {
            override fun areItemsTheSame(
                oldItem: PatientUIModel,
                newItem: PatientUIModel
            ): Boolean = newItem == oldItem

            override fun areContentsTheSame(
                oldItem: PatientUIModel,
                newItem: PatientUIModel
            ): Boolean = newItem == oldItem
        }
    }
}