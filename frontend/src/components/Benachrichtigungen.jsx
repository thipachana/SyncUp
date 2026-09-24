import { useEffect, useState } from 'react'
import { api, errorMessage } from '../api'
import { useSession } from '../session-context'
export default function Benachrichtigungen() {
  const { currentUser } = useSession()
  const [open,setOpen] = useState(false)
  const [items,setItems] = useState([])
  const [error,setError] = useState('')
  const [refresh,setRefresh] = useState(0)
  useEffect(() => {
    if(!currentUser) return
    let active=true
    const load=async () => { try { const data=await api('/api/me/benachrichtigungen');if(active){setItems(data);setError('')} } catch(e){if(active && e.name !== 'AbortError')setError(errorMessage(e))} }
    load(); const timer=setInterval(load,15000)
    return () => {active=false;clearInterval(timer)}
  },[currentUser,open,refresh])
  useEffect(() => {if(!open)return; const close=(e)=>{if(e.key==='Escape')setOpen(false)};window.addEventListener('keydown',close);return()=>window.removeEventListener('keydown',close)},[open])
  if(!currentUser)return null
  const unread=items.filter((n)=>!n.gelesen).length
  const read=async(id)=>{try{await api(`/api/me/benachrichtigungen/${id}/gelesen`,{method:'POST'});setItems((all)=>all.map((n)=>n.id===id?{...n,gelesen:true}:n))}catch(e){if(e.name!=='AbortError')setError(errorMessage(e))}}
  const remove = async (id) => {
  try {
    await api(`/api/me/benachrichtigungen/${id}`, {
      method: 'DELETE'
    })

    setItems((all) =>
      all.filter((n) => n.id !== id)
    )
  } catch (e) {
    if (e.name !== 'AbortError') {
      setError(errorMessage(e))
    }
  }
}
  return <div className="notifications">
    <button type="button" aria-label={`Benachrichtigungen, ${unread} ungelesen`} aria-expanded={open} aria-controls="notification-panel" onClick={()=>setOpen(!open)}>
      <svg aria-hidden="true" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2"><path d="M18 8a6 6 0 0 0-12 0c0 7-3 7-3 9h18c0-2-3-2-3-9M10 21h4" /></svg>
      {unread>0 && <span className="notification-count">{unread}</span>}
    </button>
    {open && <section id="notification-panel" className="notification-panel" aria-label="Benachrichtigungen">
      <h3>Benachrichtigungen</h3><button type="button" onClick={()=>setOpen(false)}>Schließen</button>
      {error && <p role="alert">{error}<button onClick={()=>setRefresh((v)=>v+1)}>Erneut laden</button></p>}
      {!error && items.length===0 && <p>Noch keine Benachrichtigungen.</p>}
      {items.map((n) => (
  <article key={n.id}>
    <p>
      <strong>{!n.gelesen && 'Neu: '}</strong>
      {n.text}
    </p>

    <small>
      {new Date(n.erstelltAm).toLocaleString('de-DE')}
    </small>

    {!n.gelesen && (
      <button
        type="button"
        onClick={() => read(n.id)}
      >
        Als gelesen markieren
      </button>
    )}

    <button
      type="button"
      onClick={() => remove(n.id)}
    >
      Löschen
    </button>
  </article>
))}
    </section>}
  </div>
}
