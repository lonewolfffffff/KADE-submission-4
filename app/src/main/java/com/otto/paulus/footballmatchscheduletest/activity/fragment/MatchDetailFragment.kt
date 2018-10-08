package com.otto.paulus.footballmatchscheduletest.activity.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.otto.paulus.footballmatchscheduletest.layout.MatchDetailUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

private const val ARG_MATCH_ID = "matchId"

class MatchDetailFragment : Fragment(), AnkoLogger {

    private var matchId: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            matchId = it.getString(ARG_MATCH_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return MatchDetailUI().createView(AnkoContext.create(inflater.context,this,false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        info(matchId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(coba:String)
    }

    companion object {
        @JvmStatic
        fun newInstance(matchId: String) =
            MatchDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_MATCH_ID, matchId)
                }
            }
    }
}
