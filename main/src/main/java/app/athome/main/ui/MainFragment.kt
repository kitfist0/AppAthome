package app.athome.main.ui

import android.widget.Toast
import androidx.lifecycle.Observer
import app.athome.core.interfaces.BaseFragment
import app.athome.core.interfaces.CoreProvider
import app.athome.main.R
import app.athome.main.di.MainComponent
import app.athome.main.nav.MainNavigation
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel, MainNavigation>(R.layout.fragment_main) {

    override fun getClassViewModel(): Class<MainViewModel> = MainViewModel::class.java
    override fun getNavInterface(): Class<MainNavigation> = MainNavigation::class.java

    override fun injectFragment(coreProvider: CoreProvider) =
        MainComponent.getComponent(coreProvider).inject(this)

    private val placeAdapter by lazy {
        PlaceAdapter(
            onEditPlaceClick = {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            },
            onRecipientChanged = {
                    recipient -> getViewModel().updatePlaceRecipient(recipient)
            }
        )
    }

    override fun onViewModelCreated() {
        recyclerView.adapter ?:let { recyclerView.adapter = placeAdapter }
        fab.setOnClickListener {
            getViewModel().insertRandomPlaceWithRecipients()
        }
        // Observe data
        getViewModel().places.observe(viewLifecycleOwner, Observer {
            placeAdapter.submitList(it)
        })
        getViewModel().emptyListEvent.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), R.string.no_places, Toast.LENGTH_LONG).show()
        })
    }
}